package com.blog.blog_login_module.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.blog_login_module.entity.User;
import com.blog.blog_login_module.repository.UserRepository;

@Service
public class UserCommandService {

	@Autowired
    private UserRepository userRepository;

    public void incrementFailedAttempts(String username) {
        userRepository.incrementFailedAttempts(username);
    }

    public int getFailedAttempts(String username) {
        return userRepository.getFailedAttempts(username);
    }

    public void lockAccount(String username) {
        userRepository.lockAccount(username);
    }

    public void resetFailedAttempts(String username) {
        userRepository.resetFailedAttempts(username);
    }

    public void updateLoginIp(String username, String ip) {
        userRepository.updateLastLoginIp(username, ip);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
