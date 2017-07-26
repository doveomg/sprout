package com.dove.sprout.common.lock.redis;

import com.dove.sprout.common.constant.LockConstant;
import com.dove.sprout.common.exception.ArgumentNotValidException;
import com.dove.sprout.common.lock.Lock;
import com.dove.sprout.common.lock.LockClient;
import com.dove.sprout.common.lock.redis.simple.RedisSimpleLockClient;

/**
 * redis 分布式锁
 * 
 * @author bod
 * @date 2016年12月21日 下午5:54:17
 *
 */
public class RedisDistributedLock implements Lock {
	private LockClient redisLock;
	private long overTime = LockConstant.DEFALUT_OVER_TIME;
	private int retryCount = LockConstant.RETRY_GET_LOCK_COUNT;
	private long retryTime = overTime / retryCount;
	private int lockType = LockConstant.LOCK_TYPE.NX_LOCK.getType();

	public RedisDistributedLock(int lockType) {
		this.lockType = lockType;
	}

	public RedisDistributedLock() {
	}

	@Override
	public boolean lock() {
		return lock(null, null, null);
	}

	@Override
	public boolean lock(String key) {
		return lock(null, null, key);
	}

	@Override
	public boolean lock(Long expiration) {
		return lock(expiration, null, null);
	}

	@Override
	public boolean lock(Long expiration, String key) {
		return lock(expiration, null, null);
	}

	@Override
	public boolean lock(Long expiration, Integer count) {
		return lock(expiration, count, null);
	}

	@Override
	public boolean lock(Long expiration, Integer count, String key) {
		return lock(expiration, count, key, null);
	}

	@Override
	public boolean lockExpire(Integer expire) {
		return lockExpire(null, expire);
	}

	@Override
	public boolean lockExpire(String key, Integer expire) {
		return lockExpire(null, key, expire);
	}

	@Override
	public boolean lockExpire(Long expiration, String key, Integer expire) {
		return lock(expiration, null, key, expire);
	}

	@Override
	public boolean lockExpire(Long expiration, Integer count, Integer expire) {
		return lock(expiration, count, null, expire);
	}

	@Override
	public boolean lock(Long expiration, Integer count, String key, Integer expire) {
		initRedisLock(key, expire);
		Long[] timeRandoms = getTimeRandoms(expiration, count);
		for (Long timeRandom : timeRandoms) {
			if (timeRandom == null) {
				break;
			}
			if (redisLock.isLock(lockType)) {
				return true;
			}
			thread(timeRandom);
		}
		return false;
	}

	/**
	 * 初始化redis锁
	 * 
	 * @author bod
	 */
	private void initRedisLock(String key, Integer expire) {
		if (lockType == LockConstant.LOCK_TYPE.SIMPLE_NX_LOCK_TIME.getType()
				|| lockType == LockConstant.LOCK_TYPE.SIMPLE_NX_LOCK.getType()) {
			redisLock = new RedisSimpleLockClient(key, expire); // 简单锁
		} else {
			redisLock = new RedisLockClient(key, expire);
		}
	}

	/**
	 * 获取随机时间数组,防止饥饿锁,次数为 count/2 ~ count*2.
	 * 
	 * @author bod
	 */
	private Long[] getTimeRandoms(Long expiration, Integer count) {
		validTime(expiration, count);
		Long overAllTime = overTime;
		Long minTime = retryTime / 2;
		Long maxTime = retryTime * 2;
		Long[] timeRandoms = new Long[2 * retryCount];
		int i = 0;
		while (overAllTime > maxTime) {
			Long theTime = (long) (Math.random() * (maxTime - minTime) + minTime);
			timeRandoms[i++] = theTime;
			overAllTime -= theTime;
		}
		if (overAllTime > 0) {
			timeRandoms[i++] = overAllTime;
		}
		return timeRandoms;
	}

	/**
	 * 验证，初始化参数
	* @author bod     
	 */
	private void validTime(Long expiration, Integer count) {
		if (expiration != null && expiration > 0) {
			overTime = expiration;
		}
		if (count != null && count > 0) {
			retryCount = count;
		}
		retryTime = overTime / retryCount;
		if (retryTime < 5) {
			throw new ArgumentNotValidException("重试时间太短！");
		}
	}

	@Override
	public boolean unlock() {
		if (redisLock != null) {
			redisLock.unlock();
			return true;
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
					RedisDistributedLock dl = null;
					try {
						dl = new RedisDistributedLock();
						if (dl.lock("bodTest")) {
							doing();
							if (dl.lock("bodTest")) {
								doing();
								dl.unlock();
							}
						} else {
							notdoing();
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						if (dl != null) {
							dl.unlock();
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
		System.out.println("这里开始工作了!" + name);
		System.out.println("当前k：" + k + "  " + name);
		//Thread.sleep(50);
		System.out.println("当前k：" + k + "  " + name +"  time:"+(System.currentTimeMillis()-beforeTime));
		k++;
	}

	public static void notdoing() {
		Thread t = Thread.currentThread();
		String name = t.getName();
		System.out.println("超时无法工作!" + name);
	}

}
