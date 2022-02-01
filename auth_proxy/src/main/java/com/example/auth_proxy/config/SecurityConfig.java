package com.example.auth_proxy.config;

import com.example.auth_proxy.security.jwt.SecurityJwtConfigurer;
import com.example.auth_proxy.security.jwt.SecurityJwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private final SecurityJwtProvider securityJwtProvider;

	private static final String[] WHITELIST = {
			"/auth/login",
			"/auth/create_user"
	};

	private static final String[] ONLYADMIN = {
			"/auth/admin/**"
	};

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.httpBasic().disable()
				.csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeRequests()
				.antMatchers(WHITELIST).permitAll()
				.antMatchers(ONLYADMIN).hasAuthority("admin")
				.anyRequest().authenticated()
				.and()
				.apply(new SecurityJwtConfigurer(securityJwtProvider));
	}
}
