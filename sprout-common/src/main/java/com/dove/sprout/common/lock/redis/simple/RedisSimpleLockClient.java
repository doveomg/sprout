package com.dove.sprout.common.lock.redis.simple;

import com.dove.sprout.common.constant.LockConstant;
import com.dove.sprout.common.lock.LockClient;
import com.dove.sprout.common.utils.JedisUtils;
import com.dove.sprout.common.utils.StringUtils;

import java.util.Date;

/**
 * 
 * @Description: 简单redis锁 - 无法重入
 * @author bod
 * @date 2016年12月26日 上午11:47:56
 *
 */
public class RedisSimpleLockClient implements LockClient {
	private Integer expire = LockConstant.DEFALUT_LOCK_OVER_TIME;// 锁，默认超时时间
	private String key = LockConstant.DEFALUT_LOCK_KEY;
	private boolean isLock = false;// 是否获取锁

	private JedisUtils jedisUtils = JedisUtils.getJedis();

	public RedisSimpleLockClient(String key) {
		setKey(key);
	}

	public RedisSimpleLockClient(String key, Integer expire) {
		setKey(key);
		setExpire(expire);
	}

	/**
	 * 采用 setnx 判断设置锁
	 * 
	 * @author bod
	 */
	public boolean isLock(int lockType) {
		if (isLockNx(lockType)) {
			isLock = true;
			return true;
		}
		return false;
	}

	private boolean isLockNx(int lockType) {
		if (lockType == LockConstant.LOCK_TYPE.SIMPLE_NX_LOCK.getType()) {
			return setNx();
		} else if (lockType == LockConstant.LOCK_TYPE.SIMPLE_NX_LOCK_TIME.getType()) {
			return setNxTime();
		}
		return false;
	}

	/**
	 * 
	 * setNx 设置锁
	 * 
	 * @author bod
	 */
	private boolean setNx() {
		Long tab = jedisUtils.setnx(key, LockConstant.REDIS_VALUE);
		if (tab == 1) {
			Long timeLong = jedisUtils.expire(key, expire);
			if (timeLong > 0) {
				return true;
			}
			// throw new ArgumentNotValidException("严重错误，这里会导致死锁！！！ key："+key);
		} else if( getKeyTime() ){// 没有成功则判断是否有超时时间，没有则设置超时时间，防止出现死锁
			if (StringUtils.isNotBlank(jedisUtils.get(key)) && jedisUtils.ttl(key) < 0) {
				jedisUtils.expire(key, expire);
			}
		}
		return false;
	}

	private static long time = new Date().getTime() ;
	/**
	 * 获取判断死锁是否超过中间时间15s
	 * @return
	 */
	private static boolean getKeyTime(){
		long t = (new Date().getTime()-time)/1000;
		if(t>15||t<0){
			time = new Date().getTime();
			return true;
		}
		return false;
	}
	
	/**
	 * 采用 setnx 设置锁,根据设置时间判断
	 * 
	 * @Title: setNxTime
	 * @author bod
	 */
	private boolean setNxTime() {
		Long expireTime = System.currentTimeMillis();// 获取当前时间
		String expireStr = String.valueOf(expireTime + expire * 1000 + LockConstant.TOLERANT_LOCK_TIME);// 超时时间+容错时间
		Long tab = jedisUtils.setnx(key, expireStr);
		if (tab == 1) {// 获取锁，结束
			return true;
		}
		String expireStrOld = jedisUtils.get(key);// 没有获取锁，则读取锁
		if (StringUtils.isNotBlank(expireStrOld) && expireTime > Long.parseLong(expireStrOld)) {// 当前时间大于获取时间(锁已超时)
			expireTime = expireTime + expire * 1000;// 当前值+超时时间
			String expireStrNow = jedisUtils.getset(key, expireStr);
			if (StringUtils.isNotBlank(expireStrNow) && expireTime > Long.valueOf(expireStrNow)) {
				// 返回值依然比当前值要小（证明值还未被其他线程修改）,如果有其他线程设置值了之后，当前时间要小于返回值。
				return true;
			}
		}
		return false;
	}

	/**
	 * 删除锁
	 * 
	 * @author bod
	 */
	public boolean unlock() {
		if (isLock) {
			Long tab = jedisUtils.del(key);
			if (tab > 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 设置key
	 * 
	 * @author bod
	 */
	private void setKey(String key) {
		if (StringUtils.isBlank(key)) {
			return;
		}
		this.key = LockConstant.LOCK_NAME_PREFIX + key;
	}

	/**
	 * 设置expire
	 * 
	 * @author bod
	 */
	private void setExpire(Integer expire) {
		if (expire == null || expire <= 0) {
			return;
		}
		this.expire = expire;
	}

	@Override
	public void lock() {
		// TODO Auto-generated method stub
		
	}

}
