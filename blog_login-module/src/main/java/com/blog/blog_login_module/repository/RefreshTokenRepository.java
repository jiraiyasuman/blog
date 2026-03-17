package com.blog.blog_login_module.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blog.blog_login_module.entity.RefreshToken;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>{

	
 /**
  * Find Refresh Token
  */
	
	Optional<RefreshToken> findByToken(String token);
	
/**
 * Delete token during logout
 */
	void deleteByToken(String token);
}
