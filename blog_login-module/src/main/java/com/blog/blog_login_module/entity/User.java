package com.blog.blog_login_module.entity;

import java.util.List;

import org.hibernate.annotations.Collate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	@Column(name="username")
	private String username;
	@Column(name="email",unique = true)
	private String email;
	@Column(name="password")
	private String password;
	@Column(name="role")
	private String role;
	@Column(name="failed_attempts")
	private int failedAttempts;
	@Column(name="account_locked")
	private boolean accountLocked;
	@Column(name="last_login_ip")
	private String lastLoginIp;
	@OneToMany
	@JoinTable(
			name="user_otp_mapping",
			joinColumns=@JoinColumn(name="user_id"),
			inverseJoinColumns = @JoinColumn(name="otp_id")
			)
	private List<OtpToken> otpTokens;
	@OneToMany
	@JoinTable(
			name="user_refresh_mapping",
			joinColumns = @JoinColumn(name="user_id"),
			inverseJoinColumns = @JoinColumn(name="refresh_id")
			)
	private List<RefreshToken> refreshTokens;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public int getFailedAttempts() {
		return failedAttempts;
	}
	public void setFailedAttempts(int failedAttempts) {
		this.failedAttempts = failedAttempts;
	}
	public boolean isAccountLocked() {
		return accountLocked;
	}
	public void setAccountLocked(boolean accountLocked) {
		this.accountLocked = accountLocked;
	}
	public String getLastLoginIp() {
		return lastLoginIp;
	}
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	public List<OtpToken> getOtpTokens() {
		return otpTokens;
	}
	public void setOtpTokens(List<OtpToken> otpTokens) {
		this.otpTokens = otpTokens;
	}
	public List<RefreshToken> getRefreshTokens() {
		return refreshTokens;
	}
	public void setRefreshTokens(List<RefreshToken> refreshTokens) {
		this.refreshTokens = refreshTokens;
	}
	public User(long id, String username, String email, String password, String role, int failedAttempts,
			boolean accountLocked, String lastLoginIp, List<OtpToken> otpTokens, List<RefreshToken> refreshTokens) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
		this.failedAttempts = failedAttempts;
		this.accountLocked = accountLocked;
		this.lastLoginIp = lastLoginIp;
		this.otpTokens = otpTokens;
		this.refreshTokens = refreshTokens;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password + ", role="
				+ role + ", failedAttempts=" + failedAttempts + ", accountLocked=" + accountLocked + ", lastLoginIp="
				+ lastLoginIp + ", otpTokens=" + otpTokens + ", refreshTokens=" + refreshTokens + "]";
	}
	
	
}
