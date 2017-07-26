package com.dove.sprout.common.lock;

public interface LockClient {
	
	public boolean isLock(int lockType);
	
	public void lock();

	public boolean unlock();

}
