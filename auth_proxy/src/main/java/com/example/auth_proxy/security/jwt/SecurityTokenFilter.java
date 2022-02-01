package com.example.auth_proxy.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
public class SecurityTokenFilter extends GenericFilterBean {
	private final SecurityJwtProvider securityJwtProvider;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String token = securityJwtProvider.resolveToken((HttpServletRequest) request);

		if (token != null && securityJwtProvider.validateToken(token)) {
			Authentication authentication = securityJwtProvider.getAuthentication(token);
			if (authentication != null) {
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		chain.doFilter(request, response);
	}
}
