package com.sprout;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableZuulProxy
@EnableRedisHttpSession
@SpringBootApplication
public class IdentificationApplication {

	public static void main(String[] args) {
		SpringApplication.run(IdentificationApplication.class, args);
	}
	
}
