package com.blog.blog_login_module.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.blog.blog_login_module.service.JwtService;
import com.blog.blog_login_module.service.TokenBlacklistService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{

	private JwtService jwtService;
	private MyUserDetailsService myUserDetailsService;
	private TokenBlacklistService blacklistService;
	@Autowired
	public JwtFilter(JwtService jwtService, MyUserDetailsService myUserDetailsService,
			TokenBlacklistService blacklistService) {
		super();
		this.jwtService = jwtService;
		this.myUserDetailsService = myUserDetailsService;
		this.blacklistService = blacklistService;
	}
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String authHeader = request.getHeader("Authorization");
			String token = null;
			String username= null;
			// extract jwt
			if(authHeader != null && authHeader.startsWith("Bearer")) {
			token = authHeader.substring(7);
			if(blacklistService.isBlacklisted(token)) {
				filterChain.doFilter(request, response);
				return;
			}
			username = jwtService.extractUserName(token);
			}
			// authenticate
			if(username != null &&
					SecurityContextHolder.getContext().getAuthentication() == null
					) {
				UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
				// validate token
				if(jwtService.validateToken(token, userDetails)) {
					UsernamePasswordAuthenticationToken authToken = 
							new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext()
					.setAuthentication(authToken);
				}
			}
		} catch (Exception e) {
			System.err.println("JWT FILTER ERROR: "+ e.getMessage());
		}
		filterChain.doFilter(request, response);
	}
	
	
	
	
	
}
