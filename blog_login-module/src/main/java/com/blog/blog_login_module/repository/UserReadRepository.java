package com.blog.blog_login_module.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.blog_login_module.entity.UserRead;

public interface UserReadRepository extends JpaRepository<UserRead, Long>{

	UserRead findByUsername(String username);
}
