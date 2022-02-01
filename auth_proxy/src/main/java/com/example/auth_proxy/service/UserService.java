package com.example.auth_proxy.service;

import com.example.auth_proxy.dto.CreateUserDto;
import com.example.auth_proxy.model.User;

import java.util.List;

public interface UserService {
	User registerUser(CreateUserDto user, String role);
	User get(Long id);
	List<User> getAll();
	boolean delete(Long id);
	User findByEmail(String email);
}
