package com.example.auth_proxy.dto;

import lombok.Data;

@Data
public class CreateUserDto {
	private String email;
	private String firstname;
	private String lastname;
	private String password;
}
