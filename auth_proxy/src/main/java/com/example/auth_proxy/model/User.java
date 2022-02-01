package com.example.auth_proxy.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String email;
	private String firstname;
	private String lastname;
	private String password;
	@Enumerated(EnumType.STRING)
	private Status status;

//	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
//	@JoinTable(name = "user_roles",
//	joinColumns = @JoinColumn(name = "user_id"),
//	inverseJoinColumns = @JoinColumn(name = "role_id"))
//	private List<Role> roles;

	@ManyToOne
	private Role role;
}
