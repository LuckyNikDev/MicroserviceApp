package com.example.auth_proxy.security.service;

import com.example.auth_proxy.model.Role;
import com.example.auth_proxy.model.Status;
import com.example.auth_proxy.model.User;
import com.example.auth_proxy.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceSecurity implements UserDetailsService {
	private final UserService userService;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userService.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("User with email " + email + " not found");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(),
				user.getPassword(),
				user.getStatus().equals(Status.ACTIVE),
				user.getStatus().equals(Status.ACTIVE),
				user.getStatus().equals(Status.ACTIVE),
				user.getStatus().equals(Status.ACTIVE),
				getAuthority(user.getRole()));
	}

	public Collection<? extends GrantedAuthority> getAuthority(Role role) {
		return role.getPermissions().stream().map(permission -> new SimpleGrantedAuthority(permission.getName())).collect(Collectors.toList());
	}
}
