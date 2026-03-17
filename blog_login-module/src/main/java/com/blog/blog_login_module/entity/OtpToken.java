package com.blog.blog_login_module.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name="otp_token")
public class OtpToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long userId;
	@Column(name="otp")
	private String otp;
	@Column(name="created_Time")
	private LocalDateTime createdTime;
	@Column(name="expiry")
	private LocalDateTime expiry;
	@Column(name="resend_count")
	private int resendCount;
	public OtpToken(long id, String otp, LocalDateTime createdTime, LocalDateTime expiry, int resendCount) {
		super();
		this.userId = id;
		this.otp = otp;
		this.createdTime = createdTime;
		this.expiry = expiry;
		this.resendCount = resendCount;
	}
	public OtpToken() {
		super();
		// TODO Auto-generated constructor stub
	}
	public long getId() {
		return userId;
	}
	public void setId(long id) {
		this.userId = id;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public LocalDateTime getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(LocalDateTime createdTime) {
		this.createdTime = createdTime;
	}
	public LocalDateTime getExpiry() {
		return expiry;
	}
	public void setExpiry(LocalDateTime expiry) {
		this.expiry = expiry;
	}
	public int getResendCount() {
		return resendCount;
	}
	public void setResendCount(int resendCount) {
		this.resendCount = resendCount;
	}
	@Override
	public String toString() {
		return "OtpToken [id=" + userId + ", otp=" + otp + ", createdTime=" + createdTime + ", expiry=" + expiry
				+ ", resendCount=" + resendCount + "]";
	}
		
}
