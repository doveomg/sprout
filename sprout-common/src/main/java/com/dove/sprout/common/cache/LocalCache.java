package com.dove.sprout.common.cache;


import com.dove.sprout.common.logger.Logger;
import com.dove.sprout.common.logger.LoggerFactory;
import com.dove.sprout.common.utils.EhCacheUtils;

/**
 * 本地缓存 
 * @author bod
 * 2017年4月26日下午3:19:07
 */
public abstract class LocalCache<T> extends BaseCache<T> {
	private static final Logger logger = LoggerFactory.getLogger(LocalCache.class);

	@Override
	public void set(String key, Object value, int exp) {
		EhCacheUtils.set(key, value, exp);
	}

	@Override
	@SuppressWarnings("unchecked")
	public T get(String key) {
		return (T)EhCacheUtils.get(key);
	}

	@Override
	public boolean del(String key) {
		return EhCacheUtils.del(key);
	}
	
	public static void main(String[] args) throws Exception {
		 String test = new LocalCache<String>(){
				@Override
				public String getValue() {
					return "test";
				}
		}.set("test", 30);
		System.out.println(test);
	}
}
