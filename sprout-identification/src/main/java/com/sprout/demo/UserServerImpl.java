package com.sprout.demo;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServerImpl implements UserServer,Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private UserRepository userRepository;
	
	public UserEntity findByAccount(String account){
		return userRepository.findByAccount(account);
	}

}
