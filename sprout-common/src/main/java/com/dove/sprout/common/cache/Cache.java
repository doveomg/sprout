package com.dove.sprout.common.cache;

public interface Cache<T> {
	//获取实际值
	public T getValue() throws Exception;
	//获取设置值 - 引用getValue()
	public T set(String key, int exp) throws Exception;
	//设置
	public void set(String key, Object value, int exp) throws Exception;
	//获取
	public T get(String key) throws Exception;
	//删除
	public boolean del(String key) throws Exception;
}
