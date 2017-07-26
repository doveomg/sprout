package com.dove.sprout.common.utils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * ehccache 工具类
 * @author bod
 * 2017年4月26日下午4:29:59
 */
public class EhCacheUtils {
	private static CacheManager cacheManager;
	private static final String LOCAL_CACHE_NAME = "localCache";

	static{
		cacheManager = CacheManager.create(EhCacheUtils.class.getClassLoader().getResourceAsStream("system/ehcache.xml"));
	}
	
	public static Cache getCache(String cacheName) {
		return cacheManager.getCache(getCacheName(cacheName));
	}

	private static String getCacheName(String cacheName) {
		return StringUtils.isNotBlank(cacheName) ? cacheName : LOCAL_CACHE_NAME;
	}

	/**
	 * 获取对象
	 * 
	 * @author bod 2017年4月26日下午4:17:17
	 */
	public static Object get(String cacheName, Object key) {
		Cache cache = getCache(cacheName);
		Element e = cache.get(key);
		if (e != null) {
			return e.getObjectValue();
		}
		return null;
	}

	public static Object get(Object key) {
		return get(null, key);
	}

	/**
	 * 设置值
	 * 
	 * @author bod 2017年4月26日下午4:23:34
	 */
	public static void set(String cacheName, Object key, Object value, int exp) {
		Cache cache = getCache(cacheName);
		Element e = new Element(key, value, exp, exp);
		cache.put(e);
	}

	public static void set(Object key, Object value, int exp) {
		set(null, key, value, exp);
	}

	/**
	 * 删除
	 * 
	 * @author bod 2017年4月26日下午4:25:00
	 */
	public static boolean del(String cacheName, Object key) {
		Cache cache = getCache(cacheName);
		return cache.remove(key);
	}

	public static boolean del(Object key) {
		return del(null,key);
	}

	/**
	 * 包含
	 * 
	 * @author bod 2017年4月26日下午4:26:49
	 */
	public static boolean contains(String cacheName, Object key) {
		Cache cache = getCache(cacheName);
		Element e = cache.get(key);
		if (e != null) {
			return true;
		}
		return false;
	}

	public static boolean contains(Object key) {
		return contains(key);
	}

}
