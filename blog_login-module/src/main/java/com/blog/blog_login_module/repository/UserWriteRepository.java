package com.blog.blog_login_module.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.blog_login_module.entity.UserWrite;

public interface UserWriteRepository extends JpaRepository<UserWrite, Long>{

	UserWrite findByEmail(String email);
	UserWrite findByUsername(String username);
	
}
