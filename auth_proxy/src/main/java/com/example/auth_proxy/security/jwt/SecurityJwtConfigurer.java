package com.example.auth_proxy.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@RequiredArgsConstructor
public class SecurityJwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
	private final SecurityJwtProvider securityJwtProvider;

	@Override
	public void configure(HttpSecurity httpSecurity) {
		SecurityTokenFilter securityTokenFilter = new SecurityTokenFilter(securityJwtProvider);
		httpSecurity.addFilterBefore(securityTokenFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
