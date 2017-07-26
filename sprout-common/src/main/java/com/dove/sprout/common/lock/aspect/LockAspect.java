package com.dove.sprout.common.lock.aspect;

import com.dove.sprout.common.constant.LockConstant.LOCK_TYPE;
import com.dove.sprout.common.exception.CommonException;
import com.dove.sprout.common.lock.Lock;
import com.dove.sprout.common.lock.redis.RedisDistributedLock;
import com.dove.sprout.common.lock.zk.ZkDistributedLock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * 
 * @Description: 分布式锁 - 切面
 * @author bod
 * @date 2016年12月24日 下午2:41:56
 *
 */
@Aspect
//@Component // 最好在需要的工程xml配置
public class LockAspect {

	@Around("@annotation(com.dove.sprout.common.lock.annotation.Lock)")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
		Method method = methodSignature.getMethod();
		com.dove.sprout.common.lock.annotation.Lock locka = method
				.getAnnotation(com.dove.sprout.common.lock.annotation.Lock.class);
		Lock lock = getLock(locka.lockType());
		try {
			if (lock.lock(locka.expiration(), locka.count(), locka.key(), locka.expire())) {
				Object result = pjp.proceed();
				return result;
			} else {
				throw new CommonException(CommonException.COMMON_EXPECTION.LOCK_OVER_TIME_REEOR);
			}
		} finally {
			try {
				lock.unlock();
			} catch (Exception e) {
				throw new CommonException(pjp.getTarget().getClass().getName() + "." + methodSignature.getName()
						+ ":解锁失败！" + locka.lockType().getName(), e);
			}
		}
	}

	private Lock getLock(LOCK_TYPE lockType) {
		Lock lock = null;
		switch (LOCK_TYPE.getLockType(lockType.getType())) {
		case ZOOKEEPER_LOCK:
			lock = new ZkDistributedLock();
			break;
		case NX_LOCK:
		case NX_LOCK_TIME:
		case SIMPLE_NX_LOCK:
		case SIMPLE_NX_LOCK_TIME:
			lock = new RedisDistributedLock(lockType.getType());
			break;
		default:
			lock = new ZkDistributedLock();
			break;
		}
		return lock;
	}
}
