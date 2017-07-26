package com.dove.sprout.common.utils;

import com.dove.sprout.common.logger.Logger;
import com.dove.sprout.common.logger.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;


public class IpUtil {
	private static final Logger logger = LoggerFactory.getLogger(IpUtil.class);

	public static String getLocalIp() {
		try {
			InetAddress ia = InetAddress.getLocalHost();
			return ia.getHostAddress().toString();
		} catch (UnknownHostException e) {
			logger.error(e.getMessage(), e);
		}
		return "";
	}

}
