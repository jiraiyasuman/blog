package com.blog.blog_login_module.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.blog_login_module.entity.User;
import com.blog.blog_login_module.repository.UserRepository;

@Service
public class UserQueryService {

	@Autowired
    private UserRepository userRepository;

    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
