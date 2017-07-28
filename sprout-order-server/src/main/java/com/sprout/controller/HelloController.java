package com.sprout.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@GetMapping("/hello")
	public String hello(){
		ServiceInstance localServiceInstance = discoveryClient.getLocalServiceInstance();
		logger.info("/hello,host:" + localServiceInstance.getHost() + "server_id:" + localServiceInstance.getServiceId());
		return "hello word";
	}
	

}
