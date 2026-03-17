package com.blog.blog_login_module.service.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.blog_login_module.entity.OtpToken;
import com.blog.blog_login_module.entity.User;
import com.blog.blog_login_module.repository.OtpTokenRepository;
import com.blog.blog_login_module.repository.UserRepository;
import com.blog.blog_login_module.util.SnowflakeIdGenerator;

@Service
public class OtpService {

    private static final int OTP_EXPIRY_MINUTES = 5;
    private static final int MAX_RESEND_ATTEMPTS = 3;
    private static final int RESEND_WAIT_SECONDS = 30;

    @Autowired
    private OtpTokenRepository otpRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SnowflakeIdGenerator idGenerator;

    private final Random random = new Random();


    /**
     * Generate OTP for login
     */
    public void generateOtp(User user) {

        String otp = String.format("%06d", random.nextInt(999999));

        OtpToken token = new OtpToken();
        token.setId(idGenerator.nextId());
        token.setOtp(otp);
        token.setExpiry(LocalDateTime.now().plusMinutes(OTP_EXPIRY_MINUTES));
        token.setResendCount(0);
        token.setCreatedTime(LocalDateTime.now());

        otpRepository.save(token);

        user.getOtpTokens().add(token);
        userRepository.save(user);

        emailService.sendOtpMail(user.getEmail(), otp);
    }


    /**
     * Verify OTP
     */
    public boolean verifyOtp(String username, String otp) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        OtpToken token = otpRepository.findTopByUserIdOrderByCreatedTimeDesc(user.getId())
                .orElseThrow(() -> new RuntimeException("OTP not found"));

        if (token.getExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired");
        }

        if (!token.getOtp().equals(otp)) {
            throw new RuntimeException("Invalid OTP");
        }

        return true;
    }


    /**
     * Resend OTP
     */
    public void resendOtp(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        OtpToken token = otpRepository
                .findTopByUserIdOrderByCreatedTimeDesc(user.getId())
                .orElseThrow(() -> new RuntimeException("OTP not found"));

        if (token.getResendCount() >= MAX_RESEND_ATTEMPTS) {
            throw new RuntimeException("Max resend attempts reached");
        }

        long seconds = Duration.between(token.getCreatedTime(),
                LocalDateTime.now()).getSeconds();

        if (seconds < RESEND_WAIT_SECONDS) {
            throw new RuntimeException("Please wait before requesting another OTP");
        }

        String newOtp = String.format("%06d", random.nextInt(999999));

        token.setOtp(newOtp);
        token.setExpiry(LocalDateTime.now().plusMinutes(OTP_EXPIRY_MINUTES));
        token.setResendCount(token.getResendCount() + 1);
        token.setCreatedTime(LocalDateTime.now());

        otpRepository.save(token);

        emailService.sendOtpMail(user.getEmail(), newOtp);
    }

}