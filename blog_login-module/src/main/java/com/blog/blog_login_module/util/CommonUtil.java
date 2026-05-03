package com.blog.blog_login_module.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.blog.blog_login_module.config.MyUserDetails;
import com.blog.blog_login_module.dto.LoginRequest;
import com.blog.blog_login_module.dto.RegisterRequest;
import com.blog.blog_login_module.entity.UserRead;
import com.blog.blog_login_module.entity.UserWrite;
import com.blog.blog_login_module.error.IllegalEmailException;
import com.blog.blog_login_module.error.IllegalPasswordException;
import com.blog.blog_login_module.error.IllegalUsernameException;
import com.blog.blog_login_module.handler.GenericResponse;
import com.blog.blog_login_module.repository.UserWriteRepository;
import com.blog.blog_login_module.service.AuthCommandService;

import jakarta.servlet.http.HttpServletRequest;
@Component
public class CommonUtil {
  private static Logger LOGGER = LoggerFactory.getLogger(CommonUtil.class);
      public ResponseEntity<?> createBuildResponse(Object data, HttpStatus status){
    	 GenericResponse response = GenericResponse.builder().responseStatus(status).status("SUCCESS").message("SUCCESS").data(data).build();
    	 LOGGER.info("CommonUtil : createBuildResponse() executed successfully");
    	 return response.create();
      }
      
      public ResponseEntity<?> createBuildResponseMessage(String message, HttpStatus status){
    	 GenericResponse response = GenericResponse.builder().responseStatus(status).status("SUCCESS").message(message).build();
    	 LOGGER.info("CommonUtil : createBuildResponseMessage() executed successfully");
    	 return response.create();
      }
      
      public static ResponseEntity<?> createErrorResponse(Object data, HttpStatus status){
    	 GenericResponse response = GenericResponse.builder().responseStatus(status).status("FAILED").message("FAILED").data(data).build();
    	 LOGGER.warn("CommonUtil : createErrorResponse() executed successfully");
    	 return response.create();
      }

      public static ResponseEntity<?> createErrorResponseMessage(String message,HttpStatus status){
    	 GenericResponse response = GenericResponse.builder().responseStatus(status).status("FAILED").message(message).build();
    	 LOGGER.warn("CommonUtil : createErrorResponseMessage() executed successfully");
    	 return response.create();
      }
      
      public String getUrl(HttpServletRequest request) {
    	  String apiUrl = request.getRequestURI().toString();
    	  apiUrl = apiUrl.replace(request.getServletPath(), "");
    	  LOGGER.info("CommonUtil : getUrl() executed successfully");
    	  return apiUrl;
      }
      
      public static UserWrite getLoggedInUser() {
    	try {
			MyUserDetails logUser = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			LOGGER.info("CommonUtil : getLoggedInUser( try clause executed successfully");
			return logUser.getUser();
		} catch (Exception e) {
			LOGGER.error("CommonUtil : getLoggedInUser() catch clause executed successfully");
			System.err.println(e);
		}  
    	return null;
    	
      }
}
	 
