//package com.dove.sprout.common.config.properties;
//
//import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
//import org.springframework.core.io.Resource;
//
//import java.io.IOException;
//import java.util.Properties;
//
///**
// *
//* @Description: 继承PropertyPlaceholderConfigurer，获取properties文件信息
//* @author bod
//* @date 2016年12月7日 下午3:32:45
//*
// */
//public class SystemProperty extends PropertyPlaceholderConfigurer {
//
//	private static Properties properties;
//
//	public void setLocations( Resource ... locations ){
//		super.setLocations(locations);
//		try {
//			SystemProperty.properties = super.mergeProperties() ;
//		} catch (IOException e) {
//			logger.error("merge properties error ... " +e.getMessage() );
//		}
//	}
//
//	/**
//	 *
//	* @Title: properties中，根据key获取value
//	* @param key 资源key
//	* @param def 默认值
//	 */
//	public static String getProperty(String key,String def){
//		String property = getProperty(key);
//		return property==null?def:property;
//	}
//
//	public static String getProperty(String key){
//		if(key==null||properties.get(key)==null){
//			return null;
//		}
//		return properties.getProperty(key).trim();
//	}
//
//}
