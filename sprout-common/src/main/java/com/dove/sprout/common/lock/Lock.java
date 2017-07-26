package com.dove.sprout.common.lock;

/**
 * 
 * 锁接口
 * 
 * @author bod
 * @date 2016年12月20日 下午6:20:34
 *
 */
public interface Lock {

	/**
	 * 获取锁 (默认配置)
	 * 
	 * @author bod
	 */
	public boolean lock();

	/**
	 * 获取锁  - 临时路径/key
	* @author bod     
	 */
	public boolean lock(String key);
	
	/**
	 * 
	* 获取锁  - 连接超时时间
	* @author bod     
	 */
	public boolean lock(Long expiration);

	public boolean lock(Long expiration, String key);

	/**
	 * 
	* 获取锁  - 连接超时时间,重试次数
	* @author bod     
	 */
	public boolean lock(Long expiration, Integer count);

	public boolean lock(Long expiration, Integer count, String key);

	/**
	 * 获取锁 - expire：锁超时时间
	* @author bod     
	 */
	boolean lockExpire(Integer expire);
	
	boolean lockExpire(String key, Integer expire);
	
	boolean lockExpire(Long expiration, String key, Integer expire);
	
	boolean lockExpire(Long expiration, Integer count, Integer expire);
	
	boolean lock(Long expiration, Integer count, String key, Integer expire);

	/**
	 * 释放锁
	 * 
	 * @author bod
	 */
	public boolean unlock();


	
}
