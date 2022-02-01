package com.example.auth_proxy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	@Enumerated(EnumType.STRING)
	private Status status;

	@JsonIgnore
	@OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
	private List<User> users;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
	@JoinTable(name = "roles_permissions",
	joinColumns = @JoinColumn(name = "role_id"),
	inverseJoinColumns = @JoinColumn(name = "permission_id"))
	private List<Permission> permissions;

	public Role() {
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

	public List<User> getUsers() {
		return this.users;
	}

	public List<Permission> getPermissions() {
		return this.permissions;
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

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	public boolean equals(final Object o) {
		if (o == this) return true;
		if (!(o instanceof Role)) return false;
		final Role other = (Role) o;
		if (!other.canEqual((Object) this)) return false;
		if (this.getId() != other.getId()) return false;
		final Object this$name = this.getName();
		final Object other$name = other.getName();
		if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
		final Object this$status = this.getStatus();
		final Object other$status = other.getStatus();
		if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
		final Object this$users = this.getUsers();
		final Object other$users = other.getUsers();
		if (this$users == null ? other$users != null : !this$users.equals(other$users)) return false;
		final Object this$permissions = this.getPermissions();
		final Object other$permissions = other.getPermissions();
		if (this$permissions == null ? other$permissions != null : !this$permissions.equals(other$permissions))
			return false;
		return true;
	}

	protected boolean canEqual(final Object other) {
		return other instanceof Role;
	}

	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		result = result * PRIME + this.getId();
		final Object $name = this.getName();
		result = result * PRIME + ($name == null ? 43 : $name.hashCode());
		final Object $status = this.getStatus();
		result = result * PRIME + ($status == null ? 43 : $status.hashCode());
		final Object $users = this.getUsers();
		result = result * PRIME + ($users == null ? 43 : $users.hashCode());
		final Object $permissions = this.getPermissions();
		result = result * PRIME + ($permissions == null ? 43 : $permissions.hashCode());
		return result;
	}

	public String toString() {
		return "Role(id=" + this.getId() + ", name=" + this.getName() + ", status=" + this.getStatus()  + ", permissions=" + this.getPermissions() + ")";
	}
}
