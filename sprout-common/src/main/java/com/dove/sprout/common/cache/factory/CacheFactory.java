package com.dove.sprout.common.cache.factory;

import com.dove.sprout.common.cache.BaseCache;
import com.dove.sprout.common.cache.LocalCache;
import com.dove.sprout.common.cache.RedisCache;
import com.dove.sprout.common.cache.SecondCache;
import com.dove.sprout.common.constant.CacheConstant.CACHE_TYPE;

public abstract class CacheFactory<T> {
	public abstract T getObjValue() throws Exception;
	public BaseCache<T> getCache(CACHE_TYPE type){
		BaseCache<T> baseCache;
		switch (type) {
		case LOCAL_CACHE:
			baseCache = new LocalCache<T>() {
				@Override
				public T getValue() throws Exception {
					return getObjValue();
				}
			};
			break;
		case REDIS_CACHE:
			baseCache = new RedisCache<T>() {
				@Override
				public T getValue() throws Exception {
					return getObjValue();
				}
			};
			break;
		case SECOND_CACHE:
			baseCache = new SecondCache<T>() {
				@Override
				public T getValue() throws Exception {
					return getObjValue();
				}
			};
			break;
		default:
			baseCache = new LocalCache<T>() {
				@Override
				public T getValue() throws Exception {
					return getObjValue();
				}
			};
			break;
		}
		return baseCache;
		
	}
}
