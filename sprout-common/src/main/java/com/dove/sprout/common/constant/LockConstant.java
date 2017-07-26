package com.dove.sprout.common.constant;

public class LockConstant {
	public static final long DEFALUT_OVER_TIME = 20000;// 默认连接超时时间
	public static final int RETRY_GET_LOCK_COUNT = 2000;// 重试次数 默认2000次
	public static final int ZK_RETRY_GET_LOCK_COUNT = 1000;// 重试次数 默认1000次
	public static final long RETRY_GET_LOCK_TIME = DEFALUT_OVER_TIME / RETRY_GET_LOCK_COUNT;// 默认重试获取锁时间 100ms

	public final static int ZOOKEEPER_SESSION_OUT_TIME = 5000;
	public final static String ZK_SPLIT = "/";
	public final static String ZOOKEEPER_LOCK_PATH = "/dove.sproutLock";// 最好先创建永久路径
	public final static String ZOOKEEPER_HOST = "127.0.0.1:2181";

	public static final String LOCK_NAME_PREFIX = "dove.sprout_";// 默认锁前缀
	public static final String DEFALUT_LOCK_KEY = LOCK_NAME_PREFIX + "DEFULT";// 默认key
	public static final int TOLERANT_LOCK_TIME = 500;// 容错毫秒数，越大越稳定，不过不要超过1S，合适就好
	public static final int DEFALUT_LOCK_OVER_TIME = 45;// 默认锁超时时间
	public static final String REDIS_VALUE = "1";// 默认值

	/**
	 * 
	 * @Description: 分布式锁类型
	 * @author bod
	 * @date 2016年12月22日 下午6:30:18
	 *
	 */
	public enum LOCK_TYPE {
		SIMPLE_ZOOKEEPER_LOCK(0, "简单zookeeper锁", "采用最小节点获取锁"), 
		ZOOKEEPER_LOCK(1, "zookeeper锁", "解决简单锁无法重入问题"), 
		SIMPLE_NX_LOCK(2, "简单setnx锁","根据setnx是否获取锁判断"), 
		SIMPLE_NX_LOCK_TIME(3, "简单setnx,getset锁", "根据设置时间判断获取锁"), 
		NX_LOCK(4, "setnx锁","解决简单锁无法重入问题"), 
		NX_LOCK_TIME(5, "setnx,getset锁", "解决简单锁无法重入问题");// 注意相同key重入锁之间只能使用相同类型锁
		private int type;
		private String name;
		private String memo;

		private LOCK_TYPE(int type, String name, String memo) {
			this.type = type;
			this.name = name;
			this.memo = memo;
		}

		public int getType() {
			return type;
		}

		public String getName() {
			return name;
		}

		public String getMemo() {
			return memo;
		}

		public static LOCK_TYPE getLockType(int type) {
			for (LOCK_TYPE lockType : LOCK_TYPE.values()) {
				if (lockType.getType() == type) {
					return lockType;
				}
			}
			return ZOOKEEPER_LOCK;
		}
	}
}
