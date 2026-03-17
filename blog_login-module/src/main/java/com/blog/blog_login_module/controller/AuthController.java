package com.blog.blog_login_module.controller;

import com.blog.blog_login_module.dto.LoginRequest;
import com.blog.blog_login_module.dto.OtpResendRequest;
import com.blog.blog_login_module.dto.OtpVerifyRequest;
import com.blog.blog_login_module.dto.RefreshTokenRequest;
import com.blog.blog_login_module.dto.RegisterRequest;
import com.blog.blog_login_module.entity.User;
import com.blog.blog_login_module.repository.UserRepository;
import com.blog.blog_login_module.service.impl.AuthService;
import com.blog.blog_login_module.service.impl.OtpService;
import com.blog.blog_login_module.util.SnowflakeIdGenerator;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth/v1/blog-login")
public class AuthController {

    private Logger log = LoggerFactory.getLogger(getClass().getName());
    private AuthService authService;

    private OtpService otpService;

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private SnowflakeIdGenerator idGenerator;

    @Autowired
    public AuthController(AuthService authService, OtpService otpService, UserRepository userRepository,
			PasswordEncoder passwordEncoder, SnowflakeIdGenerator idGenerator) {
		super();
		this.authService = authService;
		this.otpService = otpService;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.idGenerator = idGenerator;
	}

	@PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
        	log.error("Username already exists");
            return ResponseEntity.badRequest().body("Username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
        	log.error("Email already exists");
            return ResponseEntity.badRequest().body("Email already registered");
        }

        User user = new User();
        user.setId(idGenerator.nextId()); // Sharding key
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setAccountLocked(false);
        user.setFailedAttempts(0);
        user.setRole("USER");

        userRepository.save(user);
        log.info("User details saved successfully");
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request,
                                   HttpServletRequest httpRequest) {

        try {
            String response = authService.login(
                    request.getUsername(),
                    request.getPassword(),
                    httpRequest
            );
            log.info("User logged in successfully");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
        	log.error("Some error has occured"+e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody OtpVerifyRequest request) {

        try {
            Map<String, String> tokens = authService.verifyOtp(
                    request.getUsername(),
                    request.getOtp()
            );
            log.info("Verify otp has been successfully done");

            return ResponseEntity.ok(tokens);

        } catch (Exception e) {
        	log.error("Some error has occured"+e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<?> resendOtp(@RequestBody OtpResendRequest request) {

        try {
            otpService.resendOtp(request.getUsername());
            log.info("Otp resent successfully");
            return ResponseEntity.ok("OTP resent successfully");

        } catch (Exception e) {
        	log.error("Some error has occured"+e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) {

        try {
            String newAccessToken = authService.refreshAccessToken(
                    request.getRefreshToken()
            );

            Map<String, String> response = new HashMap<>();
            response.put("accessToken", newAccessToken);
            log.info("Refresh Token successfully done");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
        	log.error("Some error has occured"+e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody RefreshTokenRequest request) {

        try {
            authService.logout(request.getRefreshToken());
            log.info("Logged out successfully");
            return ResponseEntity.ok("Logged out successfully");

        } catch (Exception e) {
        	log.error("Some error has occured"+e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}