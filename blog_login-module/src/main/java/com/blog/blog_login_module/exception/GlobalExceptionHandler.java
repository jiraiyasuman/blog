package com.blog.blog_login_module.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.blog.blog_login_module.util.CommonUtil;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;

@ControllerAdvice
public class GlobalExceptionHandler {

	public Logger log = LoggerFactory.getLogger(getClass().getName());
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleException(Exception e){
		log.error("GlobalExceptionHandler : handleException() : {}",e.getMessage());
		return CommonUtil.createBuildResponseMessage(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@ExceptionHandler(SuccessException.class)
	public ResponseEntity<?> handleSuccessException(SuccessException e){
		log.error("GlobalExceptHandler : handleException() : {}",e.getMessage());
		return CommonUtil.createErrorResponse(e.getMessage(), HttpStatus.OK);
	}
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e){
		log.error("GlobalExceptionHandler : handleException() : {}",e.getMessage());
		return CommonUtil.createErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<?> handleNullPointer(Exception e){
		log.error("GlobalExceptionHandler : handleNullPointerException: {}",e.getMessage());
		return CommonUtil.createErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(Exception e){
		log.error("GlobalExceptionHandler : handleResourceNotFoundException: {}",e.getMessage());
		return CommonUtil.createBuildResponseMessage(e.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<?> handleValidationExample(ValidationException e){
		log.error("GlobalExceptionHandler : handleValidationException(): {}",e.getMessage());
		return CommonUtil.createBuildResponseMessage(e.getMessage(), HttpStatus.CONFLICT);
	}
	@ExceptionHandler(ExistDataException.class)
	public ResponseEntity<?> handleExistingDataException(ExistDataException e){
		log.error("GlobalExceptionHandler : handleExistingDataException() : {}",e.getMessage());
		return CommonUtil.createBuildResponseMessage(e.getMessage(), HttpStatus.ACCEPTED.CONFLICT);
	}
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
		log.error("GlobalExceptionHandler : handleHttpMessageNotReadableException() : {} ",e.getMessage());
		return CommonUtil.createBuildResponseMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
}
