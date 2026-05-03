package com.blog.blog_login_module.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import com.blog.blog_login_module.dto.LoginRequest;
import com.blog.blog_login_module.dto.OtpRequest;
import com.blog.blog_login_module.dto.RegisterRequest;
import com.blog.blog_login_module.endpoint.AuthEndpoint;
import com.blog.blog_login_module.error.InvalidCredentialsError;
import com.blog.blog_login_module.error.ValidationException;
import com.blog.blog_login_module.service.AuthCommandService;
import com.blog.blog_login_module.service.AuthQueryService;
import com.blog.blog_login_module.util.Validation;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/blog/login/auth")
@RequiredArgsConstructor
public class AuthController implements AuthEndpoint{

    private final AuthCommandService commandService;
    private final AuthQueryService queryService;
    private final Validation validation;
    private Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
    // ================= REGISTER =================
    @PostMapping("/register")
    @Override 
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
        	int result = validation.registerValidation(request);
        	LOGGER.info("AuthController : register() {} : execution started");
        	if(result>0) {
        		LOGGER.error("AuthController : register() : Validation error");
        		throw new ValidationException("Validation Error is thrown");
        	}
            String response = commandService.register(
                    request.getUsername(),
                    request.getEmail(),
                    request.getPassword()
            );
            LOGGER.info("AuthController : register() {} : execution ended");
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of(
                            "success", true,
                            "message", response
                    ));

        } catch (Exception ex) {
        	LOGGER.error("AuthController : register() {} : exception thrown"+ex.getMessage());
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", ex.getMessage()
            ));
        }
    }

    // ================= LOGIN =================
    @PostMapping("/login")
    @Override
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
        	int result = validation.loginValidation(request);
        	LOGGER.info("AuthController : login() {} : execution started");
        	if(result>0) {
        		LOGGER.error("AuthController : login() : Validation error");
        		throw new ValidationException("Validation Error is thrown");
        	}
            String token = commandService.login(
                    request.getEmail(),
                    request.getPassword()
            );
            if(ObjectUtils.isEmpty(token)) {
            	LOGGER.error("AuthController : login() : Invalid Credentials error");
            	throw new InvalidCredentialsError("Invalid Credentials Error");
            }
            LOGGER.info("AuthController : login() {} : execution ended");
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "token", token
            ));

        } catch (Exception ex) {
        	LOGGER.error("AuthController : login() {} : exception thrown "+ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                            "success", false,
                            "message", ex.getMessage()
                    ));
        }
    }

    // ================= VERIFY OTP =================
    @PostMapping("/verify-otp")
    @Override
    public ResponseEntity<?> verifyOtp(@RequestBody OtpRequest request) {
    	int result = validation.otpValidation(request);
    	LOGGER.info("AuthController : verifyOtp("
    			+ ") {} : execution started");
    	if(result>0) {
    		LOGGER.error("AuthController : verifyOtp() : Validation error");
    		throw new ValidationException("Validation Error is thrown");
    	}
        boolean results = commandService.verifyOtp(
                request.getEmail(),
                request.getOtp()
        );

        if (results) {
        	LOGGER.info("AuthController : verifyOtp() : Execution ended");
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "OTP verified successfully"
            ));
        }
        LOGGER.error("AuthController : verifyOtp() : OTP expired or Invalid");
        return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Invalid or expired OTP"
        ));
    }

    // ================= RESEND OTP =================
    @PostMapping("/resend-otp")
    @Override
    public ResponseEntity<?> resendOtp(@RequestParam String email) {
        try {
            LOGGER.info("AuthController : resendOtp() : resend Otp started execution");	 
            commandService.resendOtp(email);
            
            LOGGER.info("AuthController : resendOtp() : resend Otp finished execution");
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "OTP resent successfully"
            ));

        } catch (Exception ex) {
        	LOGGER.error("AuthController : resendOtp() : exception thrown in catch clause");
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", ex.getMessage()
            ));
        }
    }

    // =================     Logout         =================
    @Override
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
    	LOGGER.info("AuthController : logout() : logout started execution");
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        	LOGGER.error("AuthController : logout() : Invalid token.");
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Invalid token"
            ));
        }
        
        String token = authHeader.substring(7);

        String response = commandService.logout(token);
        
        LOGGER.info("AuthController : logout() {}: logout has finished execution");
        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", response
        ));
    }

    // ================= READ (CQRS QUERY) =================
    @GetMapping("/user/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username) {
    	LOGGER.info("AuthController : getUser() {}: getUser has started execution");
    	LOGGER.info("AuthController : getUser() {}: getUser has completed execution");
        return ResponseEntity.ok(queryService.getUser(username));
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
    	LOGGER.info("AuthController : getAllUsers() {}: getAllUsers has started execution");
    	LOGGER.info("AuthController : getAllUsers() {}: getAllUsers has ended execution");
        return ResponseEntity.ok(queryService.getAllUsers());
    }
}