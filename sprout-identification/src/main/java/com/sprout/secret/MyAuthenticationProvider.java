package com.sprout.secret;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class MyAuthenticationProvider implements AuthenticationProvider{
	
	public static Map<Thread,String> loginParameterInfo = Collections.synchronizedMap(new HashMap<>());
	
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	@Override
	public Authentication authenticate(Authentication paramAuthentication) throws AuthenticationException {
		
		String string = loginParameterInfo.get(Thread.currentThread());
		System.out.println(string);
		
		//username
        System.out.println("user name: "+paramAuthentication.getName());
        //password
        System.out.println("password: "+paramAuthentication.getCredentials());
        System.out.println("getPrincipal: "+paramAuthentication.getPrincipal());
        System.out.println("getAuthorities: "+paramAuthentication.getAuthorities());
        System.out.println("getDetails: "+paramAuthentication.getDetails());
        //上边是用户输入的信息
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(paramAuthentication.getName());
        //这里是从数据库取出的信息
        
        String inputPassword =(String)paramAuthentication.getCredentials();
        boolean equals = inputPassword.equals(userDetails.getPassword());
        if(!equals){
        	throw new IllegalArgumentException("userName or password is not right");
        }
        
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
        		paramAuthentication.getPrincipal(),paramAuthentication.getCredentials(),paramAuthentication.getAuthorities());
		
		return token;
	}

	@Override
	public boolean supports(Class<?> paramClass) {
		return true;
	}

}
