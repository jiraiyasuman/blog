package com.blog.blog_login_module.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.blog.blog_login_module.dto.LoginRequest;
import com.blog.blog_login_module.dto.OtpRequest;
import com.blog.blog_login_module.dto.RegisterRequest;
import com.blog.blog_login_module.error.IllegalEmailException;
import com.blog.blog_login_module.error.IllegalOtpException;
import com.blog.blog_login_module.error.IllegalPasswordException;
import com.blog.blog_login_module.error.IllegalUsernameException;
import com.blog.blog_login_module.repository.UserWriteRepository;
import com.blog.blog_login_module.service.AuthCommandService;

public class Validation {
    int count =0;
	public int registerValidation(RegisterRequest registerRequest) {
  	
		count =0;
  	  
  	  if(!StringUtils.hasText(registerRequest.getUsername())) {
  		  count++;
  		  throw new IllegalUsernameException("Username is invalid"); 
  	  }
  	  
  	  if(!StringUtils.hasText(registerRequest.getEmail()) || validEmail(registerRequest.getEmail())) {
  		  count ++;
  		  throw new IllegalEmailException("Email is invalid"); 
  	  }
  	  
  	  
  	  if(!StringUtils.hasText(registerRequest.getPassword())) {
  		  count++;
  		  throw new IllegalPasswordException("Password is invalid");
  	  }  	  
  	  return count;
    }
    
    public boolean validEmail(String email) {
  	  String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
  	  return email.matches(emailRegex);
    }
    
    public int loginValidation(LoginRequest loginRequest) {
    	count = 0;
    	if(!StringUtils.hasText(loginRequest.getEmail()) || validEmail(loginRequest.getEmail())) {
    		count ++; 
    		throw new IllegalEmailException("Email is invalid"); 
    	  }
    	  
    	  
    	  if(!StringUtils.hasText(loginRequest.getPassword())) {
    		  count ++;
    		  throw new IllegalPasswordException("Password is invalid");
    	  } 
    	  return count;
    }
    
    public int otpValidation(OtpRequest otpRequest) {
    	if(!StringUtils.hasText(otpRequest.getEmail()) || validEmail(otpRequest.getEmail())) {
  		  count++;
    		throw new IllegalEmailException("Email is invalid"); 
  	  }
    	
    	if(!StringUtils.hasText(otpRequest.getOtp()) || otpRequest.getOtp().length()<6 || otpRequest.getOtp().length()>6) {
  		  count++;
    		throw new IllegalOtpException("Password is invalid");
  	  }
    	
    	return count;
    	
    }
}
