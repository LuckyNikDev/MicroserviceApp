package com.example.auth_proxy.repository;

import com.example.auth_proxy.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	Role findByName(String name);
}
