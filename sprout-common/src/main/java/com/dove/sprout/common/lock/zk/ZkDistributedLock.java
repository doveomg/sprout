package com.dove.sprout.common.lock.zk;

import com.dove.sprout.common.constant.LockConstant;
import com.dove.sprout.common.exception.ArgumentNotValidException;
import com.dove.sprout.common.lock.Lock;
import com.dove.sprout.common.lock.LockClient;
import com.dove.sprout.common.lock.zk.simple.ZkSimpleLockClient;

/**
 * zookeeper 分布式锁
 * 
 * @author bod
 * @date 2016年12月21日 下午5:54:01
 *
 */
public class ZkDistributedLock implements Lock {
	private long overTime = LockConstant.DEFALUT_OVER_TIME;
	private int retryCount = LockConstant.ZK_RETRY_GET_LOCK_COUNT;
	private long retryTime = overTime / retryCount;
	private int lockType = LockConstant.LOCK_TYPE.ZOOKEEPER_LOCK.getType();

	private LockClient zkClient = null;

	@Override
	public boolean lock() {
		return lock(null, null, null);
	}

	@Override
	public boolean lock(String path) {
		return lock(null, null, path);
	}

	@Override
	public boolean lock(Long expiration) {
		return lock(expiration, null, null);
	}

	@Override
	public boolean lock(Long expiration, String path) {
		return lock(expiration, null, null);
	}

	@Override
	public boolean lock(Long expiration, Integer count) {
		// TODO Auto-generated method stub
		return lock(expiration, count, null);
	}

	@Override
	public boolean lock(Long expiration, Integer count, String path) {
		return lock(expiration, count, path, null);
	}

	@Override
	public boolean lock(Long expiration, Integer count, String path, Integer expire) {
		validTime(expiration, count);
		initZkLockClient(path);
		zkClient.lock();
		int i = 0;
		while (i++ < retryCount) {
			if (zkClient.isLock(lockType)) {
				return true;
			}
			thread(retryTime);
		}
		return false;
	}

	/**
	 * 初始化锁类型
	 * 
	 * @author bod
	 */
	private void initZkLockClient(String path) {
		if (lockType == LockConstant.LOCK_TYPE.SIMPLE_ZOOKEEPER_LOCK.getType()) {
			zkClient = new ZkSimpleLockClient(path);
		} else {
			zkClient = new ZkLockClient(path);
		}
	}

	private void validTime(Long expiration, Integer count) {
		if (expiration != null && expiration > 0) {
			overTime = expiration;
		}
		if (count != null && count > 0) {
			retryCount = count;
		}
		retryTime = overTime / retryCount;
		if (retryTime < 10) {
			throw new ArgumentNotValidException("重试时间太短！");
		}
	}

	@Override
	public boolean unlock() {
		if (zkClient != null) {
			return zkClient.unlock();
		}
		return false;
	}

	public void thread(Long time) {
		try {
			Thread.sleep(time == null ? LockConstant.RETRY_GET_LOCK_TIME : time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static Long beforeTime = null;

	public static void main(String[] args) throws InterruptedException {
		beforeTime = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			new Thread() {
				public void run() {
					ZkDistributedLock zkDistributedLock = null;
					try {
						zkDistributedLock = new ZkDistributedLock();
						if (zkDistributedLock.lock("bodTest")) {
							doing();
							ZkDistributedLock zkDistributedLock2 = new ZkDistributedLock();
							if (zkDistributedLock2.lock("bodTest")) {
								doing();
								zkDistributedLock2.unlock();
							}

						} else {
							notdoing();
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						if (zkDistributedLock != null) {
							zkDistributedLock.unlock();
						}
					}
				}
			}.start();
			Thread.sleep(10);
		}
		Thread.sleep(Long.MAX_VALUE);
	}

	private static int k = 0;

	public static void doing() throws InterruptedException {
		Thread t = Thread.currentThread();
		String name = t.getName();
		System.out.println(name + " 这里开始工作了!" + " k:" + (++k));
		// Thread.sleep(200);
		System.out.println(name + " 这里工作完成了!" + " k:" + k + "  time:" + (System.currentTimeMillis() - beforeTime));
	}

	public static void notdoing() {
		Thread t = Thread.currentThread();
		String name = t.getName();
		System.out.println("超时无法工作!" + name);
	}

	@Override
	public boolean lockExpire(Integer expire) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean lockExpire(String key, Integer expire) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean lockExpire(Long expiration, String key, Integer expire) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean lockExpire(Long expiration, Integer count, Integer expire) {
		// TODO Auto-generated method stub
		return false;
	}

}
