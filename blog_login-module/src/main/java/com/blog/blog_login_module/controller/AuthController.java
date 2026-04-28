package com.blog.blog_login_module.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.blog.blog_login_module.dto.LoginRequest;
import com.blog.blog_login_module.dto.OtpRequest;
import com.blog.blog_login_module.dto.RegisterRequest;
import com.blog.blog_login_module.service.AuthCommandService;
import com.blog.blog_login_module.service.AuthQueryService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthCommandService commandService;
    private final AuthQueryService queryService;

    // ================= REGISTER =================
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {

            String response = commandService.register(
                    request.getUsername(),
                    request.getEmail(),
                    request.getPassword()
            );

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of(
                            "success", true,
                            "message", response
                    ));

        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", ex.getMessage()
            ));
        }
    }

    // ================= LOGIN =================
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {

            String token = commandService.login(
                    request.getUsername(),
                    request.getPassword()
            );

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "token", token
            ));

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                            "success", false,
                            "message", ex.getMessage()
                    ));
        }
    }

    // ================= VERIFY OTP =================
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody OtpRequest request) {

        boolean result = commandService.verifyOtp(
                request.getEmail(),
                request.getOtp()
        );

        if (result) {
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "OTP verified successfully"
            ));
        }

        return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Invalid or expired OTP"
        ));
    }

    // ================= RESEND OTP =================
    @PostMapping("/resend-otp")
    public ResponseEntity<?> resendOtp(@RequestParam String email) {
        try {

            commandService.resendOtp(email);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "OTP resent successfully"
            ));

        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", ex.getMessage()
            ));
        }
    }

    // ================= LOGOUT =================
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Invalid token"
            ));
        }

        String token = authHeader.substring(7);

        String response = commandService.logout(token);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", response
        ));
    }

    // ================= READ (CQRS QUERY) =================
    @GetMapping("/user/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username) {
        return ResponseEntity.ok(queryService.getUser(username));
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(queryService.getAllUsers());
    }
}