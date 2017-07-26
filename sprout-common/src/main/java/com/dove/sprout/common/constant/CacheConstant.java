package com.dove.sprout.common.constant;

public class CacheConstant {
	
	/**
	 * 缓存类型
	 * @author bod
	 * 2017年4月27日上午11:47:08
	 */
	public enum CACHE_TYPE{
		LOCAL_CACHE(0),REDIS_CACHE(1),SECOND_CACHE(2);
		private int type;
		private CACHE_TYPE(int type) {
			this.type = type;
		}
		public int getType() {
			return type;
		}
		public void setType(int type) {
			this.type = type;
		}
	}

}
