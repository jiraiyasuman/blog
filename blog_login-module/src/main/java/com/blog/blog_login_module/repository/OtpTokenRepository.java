package com.blog.blog_login_module.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blog.blog_login_module.entity.OtpToken;

@Repository
public interface OtpTokenRepository extends JpaRepository<OtpToken, Long>{

	Optional<OtpToken> findTopByUserIdOrderByCreatedTimeDesc(Long userId);
	
}