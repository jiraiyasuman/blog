package com.blog.blog_login_module.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blog.blog_login_module.entity.User;
import com.blog.blog_login_module.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	
	private UserRepository userRepository;
	@Autowired
	public CustomUserDetailsService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 User user = userRepository.findByUsername(username)
	                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

	        return org.springframework.security.core.userdetails.User
	                .builder()
	                .username(user.getUsername())
	                .password(user.getPassword())
	                .roles("USER")
	                .build();
	}
	
	
}
