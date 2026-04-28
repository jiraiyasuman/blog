package com.blog.blog_login_module.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class UserRead {

	@Id
	@Column(name="id")
	private long id;
	@Column(name="username",unique=true)
	private String username;
	@Column(name="email",unique=true)
	private String email;
	@Column(name="enabled")
	private boolean enabled;
	@Column(name="locked")
	private boolean locked;
}
