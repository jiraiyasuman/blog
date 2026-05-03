package com.blog.blog_login_module.error;

import java.io.FileNotFoundException;
import java.io.ObjectInputStream.GetField;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.alibaba.ttl.threadpool.agent.internal.javassist.NotFoundException;
import com.blog.blog_login_module.util.CommonUtil;

@ControllerAdvice
public class ExceptionalHandler extends ResponseEntityExceptionHandler{

	Logger log = LoggerFactory.getLogger(ExceptionalHandler.class);	
	@ExceptionHandler
	public ResponseEntity<?> handleException(NotFoundException exc){
		log.error("GlobalExceptionHandler : handleException() : {}", exc.getMessage());
		return CommonUtil.createErrorResponseMessage(exc.getMessage(), HttpStatus.FORBIDDEN);
	}
	@ExceptionHandler
	public ResponseEntity<?> handleExcepton(Exception exc){
		log.error("GlobalExceptionHandler : handleException() : {}", exc.getMessage());
		return CommonUtil.createErrorResponseMessage(exc.getMessage(), HttpStatus.FORBIDDEN);
	}
	@Override
	protected ResponseEntity handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		log.error("GlobalExceptionHandler : handleMethodArgumentNotValid() : {}", ex.getMessage());
		return CommonUtil.createErrorResponseMessage(ex.getMessage(), HttpStatus.FORBIDDEN);
	}
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleException(Exception e) {
		log.error("GlobalExceptionHandler : handleException() : {}", e.getMessage());
		return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException e) {
		log.error("GlobalExceptionHandler : handleAccessDeniedException() : {}", e.getMessage());
		return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(SuccessException.class)
	public ResponseEntity<?> handleSuccessException(SuccessException e) {
		log.error("GlobalExceptionHandler : handleSuccessException : {}", e.getMessage());
		return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.OK);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e) {
		log.error("GlobalExceptionHandler : handleIllegalArgumentException : {}", e.getMessage());
		return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<?> handleNullPointerException(Exception e) {
		log.error("GlobalExceptionHandler : handleNullPointerException() : {}", e.getMessage());
		return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(Exception e) {
		log.error("GlobalExceptionHandler : handleResourceNotFoundException() : {}", e.getMessage());
		return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<?> handleValidationException(ValidationException e) {
		log.error("GlobalExceptionHandler : handleValidationException() : {}", e.getMessage());
		return CommonUtil.createErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(FileNotFoundException.class)
	public ResponseEntity<?> handleFileNotFoundException(FileNotFoundException e) {
		log.error("GlobalExceptionHandler : handleFileNotFoundException() : {}", e.getMessage());
		return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ExistDataException.class)
	public ResponseEntity<?> handleExistDataException(ExistDataException e) {
		log.error("GlobalExceptionHandler : handleExistDataException() : {}", e.getMessage());
		return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
		log.error("GlobalExceptionHandler : handleHttpMessageNotReadableException() : {}", e.getMessage());
		return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException e) {
		log.error("GlobalExceptionHandler : handleBadCredentialsException() : {}", e.getMessage());
		return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
}