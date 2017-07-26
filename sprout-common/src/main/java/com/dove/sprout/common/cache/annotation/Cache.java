package com.dove.sprout.common.cache.annotation;

import com.dove.sprout.common.constant.CacheConstant;
import com.dove.sprout.common.constant.CacheConstant.CACHE_TYPE;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 缓存注解
 * @author bod
 * 2017年4月27日上午9:40:02
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cache {
	String key();//key 以字符串+{num}表示输入参数 例 key="dove.sprout_{0}_{1}"
	int exp() default 3600;
	CACHE_TYPE cacheType() default CacheConstant.CACHE_TYPE.REDIS_CACHE;//缓存类型 -> 0:LocalCache 1:RedisCache 2:SecondCache
}
