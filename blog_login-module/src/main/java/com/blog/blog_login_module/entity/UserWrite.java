package com.blog.blog_login_module.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserWrite {

	@Id
	@Column(unique=true,name="id")
	private long id;
	@Column(name="email",unique=true)
	private String email;
	@Column(name="password")
	private String password;
	@Column(name="username",unique=true)
	private String username;
	@Column(name="enabled")
	private boolean enabled;
	@Column(name="locked")
	private boolean locked;
	@Column(name="lock_time")
	private LocalDateTime lockTime;
	@Column(name="failed_attempts")
	private int failedAttempts;
	@Column(name="shard_key")
	private String shardKey;
}
