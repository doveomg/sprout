package com.sprout.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.sprout.secret.MyAuthenticationProvider;
import com.sprout.secret.RestAuthenticationFailureHandler;
import com.sprout.secret.RestAuthenticationSuccessHandler;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private MyAuthenticationProvider myAuthenticationProvider;
	
	@Autowired
	private RestAuthenticationFailureHandler authenticationFailureHandler;
	
	@Autowired
	private RestAuthenticationSuccessHandler authenticationSuccessHandler;
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();

		http.formLogin()//登陆表单
				.failureHandler(authenticationFailureHandler) // failure handler
				.successHandler(authenticationSuccessHandler) // success handler
				.loginProcessingUrl("/login")// default is /login with an HTTP POST
				.permitAll(); // permit all authority
		
		http.logout()
			//接受GET请求
	        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	        //接受POST请求
	        //.logoutUrl("/logout")
	        .invalidateHttpSession(true)
	        .deleteCookies("remember-me")
	        .logoutSuccessUrl("/login")
	        .permitAll();
		
		authorizeRequests(http);
	}

	private void authorizeRequests(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/health").permitAll()
			.antMatchers("/").permitAll()
			.antMatchers("/**").authenticated();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//设置一个 UserDetailsService 进行认证
		auth.authenticationProvider(myAuthenticationProvider);
		
		//在内存中进行认证，roles 字符串设置的时候不能再加 ROLE_
		//auth.inMemoryAuthentication().withUser("feng").password("123").roles("USER");
	}
	
}
