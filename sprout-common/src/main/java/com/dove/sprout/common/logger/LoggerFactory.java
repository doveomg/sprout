package com.dove.sprout.common.logger;


import com.dove.sprout.common.logger.log4j.Log4jLoggerAdapter;

public class LoggerFactory {
	
	/**
	 * 
	* @Title: 获取logger，如果替换为其他日志框架，需要实现对应框架 LoggerAdapter
	* @param clazz
	 */
	public static Logger getLogger(Class<?> clazz){
		LoggerAdapter loggerAdapter = new Log4jLoggerAdapter();
		return loggerAdapter.getLogger(clazz);
	}
}
