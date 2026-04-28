package com.blog.blog_login_module.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class OtpVerification {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	@Column(name="email")
	private String email;
	@Column(name="otp")
	private String otp;
	@Column(name="expiry_time")
	private LocalDateTime expiryTime;
	@Column(name="resend_allowed_at")
	private LocalDateTime resendAllowedAt;
	@Column(name="verified")
	private boolean verified;
}
