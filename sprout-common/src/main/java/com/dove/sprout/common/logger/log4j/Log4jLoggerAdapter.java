package com.dove.sprout.common.logger.log4j;


import com.dove.sprout.common.logger.Logger;
import com.dove.sprout.common.logger.LoggerAdapter;
import org.apache.log4j.LogManager;

public class Log4jLoggerAdapter implements LoggerAdapter {

	public Logger getLogger(Class<?> key) {
		return new Log4jLogger(LogManager.getLogger(key));
	}

	public Logger getLogger(String key) {
		return new Log4jLogger(LogManager.getLogger(key));
	}

}