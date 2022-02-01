package com.example.auth_proxy.security.jwt;

import com.example.auth_proxy.model.Role;
import com.example.auth_proxy.security.service.UserServiceSecurity;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SecurityJwtProvider {
	private final UserServiceSecurity userServiceSecurity;

	@Value("${jwt.secret}")
	private String secretKey;
	@Value("${jwt.header}")
	private String authorizationHeader;
	@Value("${jwt.expiration}")
	private Long validityMilliSeconds;

	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}

	public String createToken(String username, Role role) {
		Claims claims = Jwts.claims().setSubject(username);
		claims.put("authorities", getAuthoritiesNames(role));

		Date now = new Date();
		Date validity = new Date(now.getTime() + validityMilliSeconds);

		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(validity)
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();
	}

	public String resolveToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if (token != null) {
			return token;
		}
		return null;
	}

	public boolean validateToken(String token) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			if (claims.getBody().getExpiration().before(new Date())) {
				return false;
			}
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			throw new JwtAuthenticationException("Jwt token is expired or invalid", HttpStatus.UNAUTHORIZED);
		}
	}

	public Authentication getAuthentication(String token) {
		UserDetails userDetails = this.userServiceSecurity.loadUserByUsername(getUsername(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	public String getUsername(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}

	private List<String> getAuthoritiesNames(Role role) {
		List<String> result = new ArrayList<>();
		role.getPermissions().forEach(permission -> {
			result.add(permission.getName());
		});
		return result;
	}
}
