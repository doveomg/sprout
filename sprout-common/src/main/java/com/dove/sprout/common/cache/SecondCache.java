package com.dove.sprout.common.cache;

import com.dove.sprout.common.logger.Logger;
import com.dove.sprout.common.logger.LoggerFactory;
import com.dove.sprout.common.utils.EhCacheUtils;
import com.dove.sprout.common.utils.JedisUtils;

/**
 * 二级缓存
 * @author bod
 * 2017年4月26日下午5:38:34
 */
public abstract class SecondCache<T> extends BaseCache<T> {
	private static final Logger logger = LoggerFactory.getLogger(SecondCache.class);

	@Override
	public void set(String key, Object value, int exp) {
		EhCacheUtils.set(key, value, exp);
		JedisUtils.setObj(key, value, exp);	
	}

	@Override
	@SuppressWarnings("unchecked")
	public T get(String key) {
		T t = (T)EhCacheUtils.get(key);//取本地缓存
		if(t==null){
			t = (T)JedisUtils.getObj(key);//取redis缓存
			setLocalCache(t,key);
		}
		return t;
	}

	/**
	 * 命中redis缓存 未命中local,设置local
	 * @author bod
	 * 2017年4月26日下午5:35:39
	 */
	private void setLocalCache(T t,String key){
		if(t!=null){
			Long exp = JedisUtils.ttlObj(key);
			if(exp>0){
				EhCacheUtils.set(key, t, exp.intValue());
			}
		}
	}
	
	@Override
	public boolean del(String key) {
		boolean local = EhCacheUtils.del(key);
		Long jedis = JedisUtils.del(key);
		return jedis>0||local;
	}
	
	public static void main(String[] args) throws Exception {
		String test = new SecondCache<String>(){
			@Override
			public String getValue() {
				return "test";
			}
		}.set("test", 300);
		System.out.println(test);
	}
	
}
