package com.dove.sprout.common.lock.zk;

import com.dove.sprout.common.constant.LockConstant;
import com.dove.sprout.common.lock.LockClient;
import com.dove.sprout.common.utils.StringUtils;
import org.apache.zookeeper.*;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * 
 * 思路 进程需要访问共享数据时, 就在"/locks"节点下创建一个sequence类型的子节点, 称为thisPath.
 * 当thisPath在所有子节点中最小时, 说明该进程获得了锁. 进程获得锁之后, 就可以访问共享资源了. 访问完成后, 需要将thisPath删除.
 * 锁由新的最小的子节点获得. 有了清晰的思路之后, 还需要补充一些细节. 进程如何知道thisPath是所有子节点中最小的呢? 可以在创建的时候,
 * 通过getChildren方法获取子节点列表, 然后在列表中找到排名比thisPath前1位的节点, 称为waitPath,
 * 然后在waitPath上注册监听, 当waitPath被删除后, 进程获得通知, 此时说明该进程获得了锁.
 * 
 * @author bod
 * @date 2016年12月21日 下午6:01:52
 *
 */
public class ZkLockClient implements Watcher, LockClient {

	private String zkTempNodePath = LockConstant.ZOOKEEPER_LOCK_PATH + LockConstant.ZK_SPLIT + "tempNode";
	private ZooKeeper zk;
	private String thisPath;
	private String waitPath;
	private CountDownLatch latch = new CountDownLatch(1);
	public boolean isLock = false;
	private ZkLockEntity zkLockEntity;
	private final static ThreadLocal<Map<String, ZkLockEntity>> threadLocal = new ThreadLocal() {
		@Override
		protected Object initialValue() {
			return new HashMap<String, ZkLockEntity>();
		}
	};

	public ZkLockClient(String path) {
		initZkLockEntity(path);
	}

	public void lock() {
		if (zkLockEntity.getCount() > 0) {//当前线程已经获取锁(锁重入)
			isTrue();
			return;
		}
		start();
	}

	private void start() {
		this.setNodePath(zkLockEntity.getKey());
		this.connectZookeeper();
		this.createNodes();
		this.watch();
	}

	/**
	 * 获取锁处理 
	* @author bod     
	 */
	private void isTrue() {
		zkLockEntity.incCount();
		this.isLock = true;
	}

	/**
	 * 初始化锁对象 
	* @author bod     
	 */
	private void initZkLockEntity(String key) {
		Map<String, ZkLockEntity> map = threadLocal.get();
		if (!map.containsKey(key)) {
			map.putIfAbsent(key, new ZkLockEntity(key));
		}
		this.zkLockEntity = map.get(key);
	}

	/**
	 * 连接zookeeper
	 * 
	 * @author bod
	 */
	private ZooKeeper connectZookeeper() {
		try {
			zk = new ZooKeeper(LockConstant.ZOOKEEPER_HOST, LockConstant.ZOOKEEPER_SESSION_OUT_TIME, this);
			latch.await();
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return zk;
	}

	/**
	 * 
	 * 删除节点，关闭连接
	 * 
	 * @author bod
	 */
	public boolean unlock() {
		try {
			if (zkLockEntity.getCount() > 0) {
				zkLockEntity.decCount();
				if (zkLockEntity.getCount() == 0 && zk != null) {
					zk.delete(this.thisPath, -1);// 删除节点
					zk.close();
				}
			}
		} catch (InterruptedException | KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 创建节点
	 * 
	 * @throws InterruptedException
	 */
	private void createNodes() {
		if (zk == null) {
			return;
		}
		try {
			synchronized (this) {
				if (zk.exists(LockConstant.ZOOKEEPER_LOCK_PATH, false) == null) {// 创建永久节点，建议提前创建(否则有并发问题)，不需要此代码
					zk.create(LockConstant.ZOOKEEPER_LOCK_PATH, null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
				}
				// 创建当前临时顺序节点
				thisPath = zk.create(zkTempNodePath, null, Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
			}
		} catch (KeeperException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void watch() {
		if (zk == null) {
			return;
		}
		try {
			List<String> nodeList = zk.getChildren(LockConstant.ZOOKEEPER_LOCK_PATH, false);
			if (nodeList.size() == 0) {// 只有当前节点
				isTrue();
			} else {
				String thisNode = thisPath
						.substring((LockConstant.ZOOKEEPER_LOCK_PATH + LockConstant.ZK_SPLIT).length());
				Collections.sort(nodeList);
				int index = nodeList.indexOf(thisNode);
				if (index < 0) {// 节点不存在，基本不会发生
					return;
				} else if (index == 0) {
					isTrue();// 当前节点排到第一个，可以执行了
				} else {
					this.waitPath = LockConstant.ZOOKEEPER_LOCK_PATH + LockConstant.ZK_SPLIT + nodeList.get(index - 1);// 上一节点
					// 重新注册监听, 并判断此时waitPath是否已删除
					if (zk.exists(waitPath, true) == null) {
						// 如果监听的节点在这一步也被删除，调用自身判断、监听
						watch();
					}
				}
			}
		} catch (KeeperException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void process(WatchedEvent event) {
		KeeperState keeperState = event.getState();
		if (keeperState == KeeperState.SyncConnected && event.getType() == Event.EventType.None) {
			// 连接zookeeper成功
			latch.countDown();
		}
		// 发生了waitPath的删除事件
		if (event.getType() == EventType.NodeDeleted && event.getPath().equals(waitPath)) {
			this.watch();
		}
	}

	private void setNodePath(String path) {
		if (StringUtils.isNotBlank(path)) {
			path.replaceAll(LockConstant.ZK_SPLIT, "");
			this.zkTempNodePath = LockConstant.ZOOKEEPER_LOCK_PATH + LockConstant.ZK_SPLIT + path;
		}
	}

	@Override
	public boolean isLock(int lockType) {
		// TODO Auto-generated method stub
		return this.isLock;
	}

}
