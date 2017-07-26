package com.dove.sprout.common.cache;


import com.dove.sprout.common.logger.Logger;
import com.dove.sprout.common.logger.LoggerFactory;
import com.dove.sprout.common.utils.JedisUtils;

/**
 * redis 缓存
 * @author bod
 * 2017年4月26日下午2:03:34
 */
public abstract class RedisCache<T> extends BaseCache<T>{
	private static final Logger logger = LoggerFactory.getLogger(RedisCache.class);

	@Override
	public void set(String key, Object value, int exp) {
		JedisUtils.setObj(key, value, exp);		
	}

	@Override
	@SuppressWarnings("unchecked")
	public T get(String key) {
		return (T)JedisUtils.getObj(key);
	}

	@Override
	public boolean del(String key) {
		Long flag = JedisUtils.del(key);
		return flag>0?true:false;
	}
	
	public static void main(String[] args) throws Exception {
		String test = new RedisCache<String>(){
				@Override
				public String getValue() {
					return "test";
				}
		}.set("test", 30);
		System.out.println(test);
	}

}
