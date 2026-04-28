package com.blog.blog_login_module.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.blog.blog_login_module.entity.UserRead;
import com.blog.blog_login_module.repository.UserReadRepository;

@Service
public class UserQueryCacheService {

	private UserReadRepository readRepository;

	@Autowired
	public UserQueryCacheService(UserReadRepository readRepository) {
		super();
		this.readRepository = readRepository;
	}
	@Cacheable(value = "users" , key = "#username")
	public UserRead getUser(String username) {
		System.out.println("CACHE MISS -> DB HIT");
		return readRepository.findByUsername(username);
	}
	@CacheEvict(value = "users" , key = "#username")
	public void evictUser(String username) {
		System.out.println("CACHE EVICT -> "+username);
	}
}