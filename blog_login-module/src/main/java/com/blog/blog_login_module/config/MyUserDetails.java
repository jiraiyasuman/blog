package com.blog.blog_login_module.config;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.blog.blog_login_module.entity.UserWrite;

public class MyUserDetails implements UserDetails{

	private final UserWrite user;
	@Autowired
	public MyUserDetails(UserWrite user) {
		super();
		this.user = user;
	}

	/**
     * AUTHORITIES / ROLES
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Future: fetch from DB
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    /**
     * PASSWORD 
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * USERNAME
     */
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    /**
     * ACCOUNT STATUS FLAGS
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.isLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled(); // OTP verified
    }

    /**
     * OPTIONAL: expose underlying user
     */
    public UserWrite getUser() {
        return user;
    }

}
