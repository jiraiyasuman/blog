package com.blog.blog_login_module.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.blog.blog_login_module.entity.UserRead;
import com.blog.blog_login_module.entity.UserWrite;
import com.blog.blog_login_module.repository.UserReadRepository;
import com.blog.blog_login_module.repository.UserWriteRepository;

public class MyUserDetailsService implements UserDetailsService{
	
	
	private UserReadRepository userReadRepository;
	private UserWriteRepository userWriteRepository;
	@Autowired
	public MyUserDetailsService(UserReadRepository userReadRepository, UserWriteRepository userWriteRepository) {
		super();
		this.userReadRepository = userReadRepository;
		this.userWriteRepository = userWriteRepository;
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		// read from read db(CQRS Query side)
		UserRead userRead = userReadRepository.findByUsername(username);
		if(userRead == null) {
			throw new UsernameNotFoundException("User not found");
		}
		// fetch password from write db(source of truth)
		UserWrite userWrite = userWriteRepository.findByUsername(username);
		if(userWrite == null) {
			throw new UsernameNotFoundException("User not found in write DB");	
		}
		// Additional  validations
		if(userWrite.isLocked()) {
			throw new LockedException("Account is Locked");
		}
		if(!userWrite.isEnabled()) {
			throw new DisabledException("OTP verification pending");
		}
		// Return spring security userDetails
		return new org.springframework.security.core.userdetails.User(
                userWrite.getUsername(),
                userWrite.getPassword(),
                true,   // account non-expired
                true,   // credentials non-expired
                !userWrite.isLocked(),
                true,   // account enabled
                new java.util.ArrayList<>()
        );
	}

	
}
