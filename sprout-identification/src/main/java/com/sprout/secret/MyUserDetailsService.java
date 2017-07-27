package com.sprout.secret;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sprout.demo.RoleEntity;
import com.sprout.demo.UserEntity;
import com.sprout.demo.UserServer;

@Service
public class MyUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserServer userServer;
	
	@Override
	public UserDetails loadUserByUsername(String paramString) throws UsernameNotFoundException {
		UserEntity userEntity = userServer.findByAccount(paramString);
		
		String account = userEntity.getAccount();
		String password = userEntity.getPassword();
		
		Set<GrantedAuthority> roles = this.getRoles(userEntity);
		User user = new User(account, password, roles);
		
		return user;
	}
	
	public Set<GrantedAuthority> getRoles(UserEntity userEntity){
		
		Set<GrantedAuthority> grantedAuthoritys = new HashSet<>();
		
		Set<RoleEntity> roles = userEntity.getRoles();
		for (RoleEntity roleEntity : roles) {
			String roleName = roleEntity.getRoleName();
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_" + roleName);
			grantedAuthoritys.add(grantedAuthority);
		}
		
		return grantedAuthoritys;
		
	}
	

}
