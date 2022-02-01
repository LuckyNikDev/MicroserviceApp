package com.example.auth_proxy.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "permissions")
public class Permission {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	@Enumerated(EnumType.STRING)
	private Status status;

	@JsonIgnore
	@ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY)
	private List<Role> roles;

	public Permission() {
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public Status getStatus() {
		return this.status;
	}

	public List<Role> getRoles() {
		return this.roles;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public boolean equals(final Object o) {
		if (o == this) return true;
		if (!(o instanceof Permission)) return false;
		final Permission other = (Permission) o;
		if (!other.canEqual((Object) this)) return false;
		if (this.getId() != other.getId()) return false;
		final Object this$name = this.getName();
		final Object other$name = other.getName();
		if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
		final Object this$status = this.getStatus();
		final Object other$status = other.getStatus();
		if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
		final Object this$roles = this.getRoles();
		final Object other$roles = other.getRoles();
		if (this$roles == null ? other$roles != null : !this$roles.equals(other$roles)) return false;
		return true;
	}

	protected boolean canEqual(final Object other) {
		return other instanceof Permission;
	}

	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		result = result * PRIME + this.getId();
		final Object $name = this.getName();
		result = result * PRIME + ($name == null ? 43 : $name.hashCode());
		final Object $status = this.getStatus();
		result = result * PRIME + ($status == null ? 43 : $status.hashCode());
		final Object $roles = this.getRoles();
		result = result * PRIME + ($roles == null ? 43 : $roles.hashCode());
		return result;
	}

	public String toString() {
		return "Permission(id=" + this.getId() + ", name=" + this.getName() + ", status=" + this.getStatus() + ")";
	}
}
