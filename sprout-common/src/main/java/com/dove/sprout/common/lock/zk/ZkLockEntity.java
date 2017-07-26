package com.dove.sprout.common.lock.zk;

import java.util.UUID;

/**
 * 
* @Description: 线程锁对象
* @author bod
* @date 2016年12月26日 下午3:58:09 
*
 */
public class ZkLockEntity {

	private Integer count = 0;

	private String key;
	
	private String id = UUID.randomUUID().toString(); 

	public ZkLockEntity(String key) {
		this.key = key;
	}

	public void incCount() {
		count++;
	}

	public void decCount() {
		count--;
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
