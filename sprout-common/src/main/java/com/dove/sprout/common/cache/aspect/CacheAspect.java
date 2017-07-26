package com.dove.sprout.common.cache.aspect;

import com.dove.sprout.common.cache.BaseCache;
import com.dove.sprout.common.cache.factory.CacheFactory;
import com.dove.sprout.common.logger.Logger;
import com.dove.sprout.common.logger.LoggerFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 缓存 aop 
 * @author bod
 * 2017年4月27日下午3:29:18
 */
@Aspect
public class CacheAspect {
	private static final Logger logger = LoggerFactory.getLogger(CacheAspect.class);
	@Around("@annotation(com.dove.sprout.common.cache.annotation.Cache)")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
		Method method = methodSignature.getMethod();
		com.dove.sprout.common.cache.annotation.Cache cache = method
				.getAnnotation(com.dove.sprout.common.cache.annotation.Cache.class);
		String key = getKey(cache.key(),pjp.getArgs());
		BaseCache baseCache = new CacheFactory() {
			@Override
			public Object getObjValue() throws Exception {
				try {
					return pjp.proceed();
				} catch (Throwable e) {
					logger.error(e);
					throw new Exception(e);
				}
			}
		}.getCache(cache.cacheType());
		return baseCache.set(key, cache.exp());
	}
	
	/**
	 * 获取key
	 * @author bod
	 * 2017年4月27日下午2:48:09
	 */
	private String getKey(String key,Object[] args){
        String reg = "\\{((?:(?!\\})[\\s\\S])*)\\}";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(key);
        while(matcher.find())
        {
        	Integer index = Integer.parseInt(matcher.group(1));
        	key=key.replace("{"+index+"}", String.valueOf(args[index]));
        }
		return key;
		
	}
	
	/*例如
	@Cache(key="liming_{1}_{2}",exp=120,cacheType=CACHE_TYPE.LOCAL_CACHE)
	public String cache(Integer a,String b,Boolean c){
		return "test";
	}*/
	
}
