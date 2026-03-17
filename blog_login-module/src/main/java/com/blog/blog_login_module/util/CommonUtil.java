package com.blog.blog_login_module.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.blog.blog_login_module.handler.GenericResponse;

import jakarta.servlet.http.HttpServletRequest;

public class CommonUtil {

	public static ResponseEntity<?> createBuilderResponse(Object data, HttpStatus status){
		GenericResponse response = GenericResponse.builder().responseStatus(status).status("Success").message("Success")
				.data(data).build();
		return response.create();
	}
	
	public static ResponseEntity<?> createBuildResponseMessage(String message,HttpStatus status){
		GenericResponse response = GenericResponse.builder().responseStatus(status).status("Success").message("Success")
				.build();
		return response.create();
	}
	
	public static ResponseEntity<?> createErrorResponse(Object data, HttpStatus status){
		GenericResponse response = GenericResponse.builder().responseStatus(status).status("Failed").message("Failed")
				.data(data).build();
		return response.create();
	}
	
	public static String getUrl(HttpServletRequest request) {
		String apiUri = request.getRequestURI().toString();
		apiUri = apiUri.replace(request.getServletPath(), "");
		return apiUri;
	}
}
