package com.example.auth_proxy.service.impl;

import com.example.auth_proxy.dto.CreateUserDto;
import com.example.auth_proxy.model.Role;
import com.example.auth_proxy.model.Status;
import com.example.auth_proxy.model.User;
import com.example.auth_proxy.repository.RoleRepository;
import com.example.auth_proxy.repository.UserRepository;
import com.example.auth_proxy.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;

	public User registerUser(CreateUserDto dtoUser, String role) {
		User user = new User();
		Role roleUser = roleRepository.findByName(role);

		user.setEmail(dtoUser.getEmail());
		user.setFirstname(dtoUser.getFirstname());
		user.setLastname(dtoUser.getFirstname());
		user.setPassword(passwordEncoder.encode(dtoUser.getPassword()));
		user.setStatus(Status.ACTIVE);
		user.setRole(roleUser);

		User registeredUser = userRepository.save(user);

		log.info("IN register - user: {} successfully registered", registeredUser);
		return registeredUser;
	}

	public User get(Long id) {
		User result = userRepository.findById(id).orElse(null);
		if (result == null) {
			log.info("IN findById - user not found by id: {}", id);
			return null;
		}
		log.info("IN findById - user: {} found by id: {}", result, id);
		return result;
	}

	public User findByEmail(String email) {
		User result = userRepository.findByEmail(email);
		if (result == null) {
			log.info("IN findById - user not found by email: {}", email);
			return null;
		}
		log.info("IN findById - user: {} found by email: {}", result, email);
		return result;
	}

	public List<User> getAll() {
		List<User> result = userRepository.findAll();
		log.info("IN getAll - {} users found", result.size());
		return result;
	}

	public boolean delete(Long id) {
		try {
			userRepository.deleteById(id);
			log.info("IN delete - user with id: {} successfully deleted", id);
			return true;
		} catch (EmptyResultDataAccessException e) {
			log.info("IN delete - user not found with id: {}", id);
			return false;
		}
	}
}
