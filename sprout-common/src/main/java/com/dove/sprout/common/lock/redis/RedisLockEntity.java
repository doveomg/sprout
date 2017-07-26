package com.dove.sprout.common.lock.redis;

import java.util.UUID;

/**
 * 
* @Description: 线程锁对象
* @author bod
* @date 2016年12月26日 下午3:58:09 
*
 */
public class RedisLockEntity {

	private Integer count = 0;

	private String key;

	private Integer expire;

	private String id = UUID.randomUUID().toString(); 

	public RedisLockEntity(String key) {
		this.key = key;
	}

	public RedisLockEntity(String key, Integer expire) {
		this.key = key;
		this.expire = expire;
	}

	public void incCount() {
		count++;
	}

	public void decCount() {
		count--;
	}

	public Integer getExpire() {
		return expire;
	}

	public void setExpire(Integer expire) {
		this.expire = expire;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
