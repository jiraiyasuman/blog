package com.blog.blog_login_module.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity(name="refresh_token")
public class RefreshToken {

	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name="id")
	    private Long id;

	    @Column(name="token",nullable = false, unique = true, length = 500)
	    private String token;

	    @Column(nullable = false,name="expiry")
	    private LocalDateTime expiry;

	    @Column(nullable = false,name="created_at")
	    private LocalDateTime createdAt;

	    @Column(name= "created_by_ip")
	    private String createdByIp;

	    @Column(name="revoked")
	    private boolean revoked = false;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public LocalDateTime getExpiry() {
			return expiry;
		}

		public void setExpiry(LocalDateTime expiry) {
			this.expiry = expiry;
		}

		public LocalDateTime getCreatedAt() {
			return createdAt;
		}

		public void setCreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
		}

		public String getCreatedByIp() {
			return createdByIp;
		}

		public void setCreatedByIp(String createdByIp) {
			this.createdByIp = createdByIp;
		}

		public boolean isRevoked() {
			return revoked;
		}

		public void setRevoked(boolean revoked) {
			this.revoked = revoked;
		}

		public RefreshToken(Long id, String token, LocalDateTime expiry, LocalDateTime createdAt, String createdByIp,
				boolean revoked) {
			super();
			this.id = id;
			this.token = token;
			this.expiry = expiry;
			this.createdAt = createdAt;
			this.createdByIp = createdByIp;
			this.revoked = revoked;
		}

		public RefreshToken() {
			super();
			// TODO Auto-generated constructor stub
		}

		@Override
		public String toString() {
			return "RefreshToken [id=" + id + ", token=" + token + ", expiry=" + expiry + ", createdAt=" + createdAt
					+ ", createdByIp=" + createdByIp + ", revoked=" + revoked + "]";
		}
	    
	    
}
