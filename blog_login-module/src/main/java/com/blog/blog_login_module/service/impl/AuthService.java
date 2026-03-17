package com.blog.blog_login_module.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.blog_login_module.entity.RefreshToken;
import com.blog.blog_login_module.entity.User;
import com.blog.blog_login_module.repository.RefreshTokenRepository;
import com.blog.blog_login_module.repository.UserRepository;
import com.blog.blog_login_module.security.JwtUtil;
import com.blog.blog_login_module.util.SnowflakeIdGenerator;

import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private static final int MAX_FAILED_ATTEMPTS = 3;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OtpService otpService;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private SnowflakeIdGenerator idGenerator;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RateLimiterService rateLimiterService;


    public String login(String username, String password, HttpServletRequest request) {

        String ip = request.getRemoteAddr();

        // Rate limit check
        if (!rateLimiterService.allowRequest(ip)) {
            throw new RuntimeException("Too many requests. Try again later.");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.isAccountLocked()) {
            throw new RuntimeException("Account is locked due to multiple failed attempts.");
        }

        // password validation
        if (!passwordEncoder.matches(password, user.getPassword())) {

            userRepository.incrementFailedAttempts(username);

            Integer attempts = userRepository.getFailedAttempts(username);

            if (attempts >= MAX_FAILED_ATTEMPTS) {
                userRepository.lockAccount(username);
                throw new RuntimeException("Account locked after 3 failed attempts.");
            }

            throw new RuntimeException("Invalid password");
        }

        // reset failed attempts
        userRepository.resetFailedAttempts(username);

        // track login IP
        userRepository.updateLastLoginIp(username, ip);

        // generate OTP
        otpService.generateOtp(user);

        return "OTP sent to registered email.";
    }



    public Map<String, String> verifyOtp(String username, String otp) {

        boolean valid = otpService.verifyOtp(username, otp);

        if (!valid) {
            throw new RuntimeException("Invalid OTP");
        }

        String accessToken = jwtUtil.generateToken(username);

        String refreshToken = createRefreshToken(username);

        Map<String, String> tokens = new HashMap<>();

        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);

        return tokens;
    }



    private String createRefreshToken(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtil.generateRefreshToken(username);

        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setId(idGenerator.nextId());
        refreshToken.setToken(token);
        refreshToken.setExpiry(LocalDateTime.now().plusDays(7));

        refreshTokenRepository.save(refreshToken);

        user.getRefreshTokens().add(refreshToken);

        userRepository.save(user);

        return token;
    }



    public String refreshAccessToken(String refreshToken) {

        RefreshToken storedToken = refreshTokenRepository
                .findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (storedToken.getExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Refresh token expired");
        }

        String username = jwtUtil.extractUsername(refreshToken);

        return jwtUtil.generateToken(username);
    }


    public void logout(String refreshToken) {

        refreshTokenRepository.deleteByToken(refreshToken);
    }

}