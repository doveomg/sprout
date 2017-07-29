package com.sprout.demo;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by miaorf on 2016/8/2.
 */
@RestController
@Slf4j
public class HelloController {
	
	/**
	 * 健康监测
	 * @return
	 */
	@RequestMapping(value = {"/","/health"})
	public Map<String,String> check(){

		log.info("slkdja  ----->  {} " , "AAAAA");

		HashMap<String, String> hashMap = new HashMap<String,String>();
		hashMap.put("status", "ok");
		return hashMap;
	}
	
    
    /**
     * 登录成功，跳转到欢迎页面
     * @return
     */
    @RequestMapping("/login/success")
    public Map<String,String> loginSuccess(){
    	Map<String,String> map = new HashMap<>();
    	map.put("1", "feng");
    	
    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	
    	System.out.println(principal);
    	
    	return map;
    	
    }
    
}
