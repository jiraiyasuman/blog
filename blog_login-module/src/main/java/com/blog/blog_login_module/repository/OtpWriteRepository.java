package com.blog.blog_login_module.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.blog_login_module.entity.OtpVerification;

public interface OtpWriteRepository  extends JpaRepository<OtpVerification, Long>{

	OtpVerification findTopByEmailOrderByExpiryTimeDesc(String email);
}
