package com.dove.sprout.common.cache;


import com.dove.sprout.common.logger.Logger;
import com.dove.sprout.common.logger.LoggerFactory;

/**
 * 缓存基类
 * @author bod
 * 2017年4月26日下午3:14:23
 */
public abstract class BaseCache<T> implements Cache<T> {
	private static final Logger logger = LoggerFactory.getLogger(BaseCache.class);

	/**
	 * 设置缓存 
	 * exp 过期时间（s）
	 * @author bod
	 * 2017年4月26日下午2:46:33
	 * @throws Exception 
	 */
	@Override
	public T set(String key, int exp) throws Exception {
		T t = get(key);
		if(t==null){
			return getMiss(key,exp);
		}
		return getHit(t,key,exp);
	}
	
	/**
	 * 缓存未命中 获取值，设置缓存
	 * @author bod
	 * 2017年4月26日下午3:01:53
	 * @throws Exception 
	 */
	private T getMiss(String key, int exp) throws Exception{
		T t = null;
		//RedisDistributedLock lock = new RedisDistributedLock();//分布式锁 -- 含有分布式缓存可使用
		//if(lock.lock(key)){doing...}
		synchronized (key.intern()) {
			t = get(key);
			if(t==null){//防止线程并发访问数据源
				t = getValue();
			}else{//其他线程设置了值
				return getHit(t,key,exp);
			}
		}
		setT(key,t,exp);
		logger.info(" cache miss... key="+key+" exp="+exp);
		return t;
	}
	
	/**
	 * 设置缓存 - 防止缓存穿透
	 * @author bod
	 * 2017年4月26日下午3:06:08
	 * @throws Exception 
	 */
	private void setT(String key,T t,int exp) throws Exception{
		if(t!=null){
			set(key, t, exp);
		}else{
			set(key, "NULL", exp);//防止缓存穿透
		}
	}
	
	/**
	 * 缓存命中
	 * @author bod
	 * 2017年4月26日下午3:02:24
	 */
	private T getHit(T t,String key, int exp){
		if("NULL".equals(t)){
			logger.info(" cache hit null ... key="+key+" exp="+exp);
			return null;
		}
		logger.info(" cache hit ... key="+key+" exp="+exp);
		return t;
	}
}
