package com.blog.blog_login_module.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.blog_login_module.entity.UserRead;
import com.blog.blog_login_module.repository.UserReadRepository;
@Service
public class AuthQueryService {

	private final UserReadRepository userReadRepository;
	private final UserQueryCacheService userQueryCacheService;
	@Autowired
	public AuthQueryService(UserReadRepository userReadRepository, UserQueryCacheService userQueryCacheService) {
		super();
		this.userReadRepository = userReadRepository;
		this.userQueryCacheService = userQueryCacheService;
	}
	
	public UserRead getUser(String username) {
		return userQueryCacheService.getUser(username);
	}
	
	public List<UserRead> getAllUsers(){
		return userReadRepository.findAll();
	}
	
	public boolean isUserLocked(String username) {
		UserRead user = userQueryCacheService.getUser(username);
		return user!=null && user.isLocked(); 
	}
	public boolean isUserEnabled(String username) {
		UserRead user= userQueryCacheService.getUser(username);
		return user != null && user.isEnabled();
	}
}