package com.blog.blog_login_module.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.blog_login_module.entity.UserRead;
import com.blog.blog_login_module.repository.UserReadRepository;
import com.blog.blog_login_module.repository.UserWriteRepository;

import lombok.RequiredArgsConstructor;

@Service
public class UserService {

	
	private final AuthCommandService commandService;
	private final AuthQueryService authQueryService;
	@Autowired
	public UserService(AuthCommandService commandService, AuthQueryService authQueryService) {
		super();
		this.commandService = commandService;
		this.authQueryService = authQueryService;
	}
	
	/**
	 * Write Side
	 */
	public String register(String username, String email, String password) {
		return commandService.register(username, email, password);
	}
	
	public void updateUser(long userId, String email) {
		commandService.updateUser(userId, email);
	}
	
	public void deleteUser(long userId) {
		commandService.deleteUser(userId);
	}
	
	
	/**
	 * Read Side
	 */
	
	public UserRead getUser(String username) {
		return authQueryService.getUser(username);
	}
	
	public List<UserRead> getAllUsers(){
		return authQueryService.getAllUsers();
	}
	
	/**
	 * STATUS CHECKS
	 */
	
	public boolean isUserLocked(String username) {
		return authQueryService.isUserLocked(username);
	}
	
	public boolean isUserEnabled(String username) {
		return authQueryService.isUserEnabled(username);
	}
}