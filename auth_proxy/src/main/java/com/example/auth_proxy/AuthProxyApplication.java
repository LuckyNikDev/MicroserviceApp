package com.example.auth_proxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class AuthProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthProxyApplication.class, args);
	}

}
