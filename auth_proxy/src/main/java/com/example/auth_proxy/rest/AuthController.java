package com.example.auth_proxy.rest;


import com.example.auth_proxy.dto.AuthenticationDtoUser;
import com.example.auth_proxy.dto.CreateUserDto;
import com.example.auth_proxy.model.User;
import com.example.auth_proxy.security.jwt.SecurityJwtProvider;
import com.example.auth_proxy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
	private final UserService userService;
	private final AuthenticationManager authenticationManager;
	private final SecurityJwtProvider securityJwtProvider;

	@GetMapping("/admin")
	public List<User> permitAllPage() {
		return userService.getAll();
	}

	@PostMapping("/admin/create_admin")
	public User createAdmin(@RequestBody CreateUserDto userDto) {
		return userService.registerUser(userDto, "ROLE_ADMIN");
	}

	@DeleteMapping("/admin/{id}")
	public ResponseEntity deleteUser(@PathVariable(name = "id") Long id) {
		if (userService.delete(id)) {
			return new ResponseEntity("User with id:" + id + " is deleted", HttpStatus.OK);
		} else {
			return new ResponseEntity("User not found with id:" + id, HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/create_user")
	public User createUser(@RequestBody CreateUserDto user) {
		return userService.registerUser(user, "ROLE_USER");
	}

	@PostMapping("/login")
	public ResponseEntity login(@RequestBody AuthenticationDtoUser dto) {
		try {
			String email = dto.getEmail();
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, dto.getPassword()));
			User user = userService.findByEmail(email);

			if (user == null) {
				throw new UsernameNotFoundException("User with username: " + email + " not found");
			}

			String token = securityJwtProvider.createToken(email, user.getRole());
			Map<Object, Object> response = new HashMap<>();
			response.put("username", email);
			response.put("token", token);
			return ResponseEntity.ok(response);
		} catch (AuthenticationException e) {
			throw new BadCredentialsException("Invalid username or password");
		}
	}
}
