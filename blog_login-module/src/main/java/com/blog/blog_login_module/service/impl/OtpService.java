package com.blog.blog_login_module.service.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private Logger log = LoggerFactory.getLogger(getClass().getName());
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
        log.info("TOken successfully generated");
        otpRepository.save(token);

        user.getOtpTokens().add(token);
        log.info("User saved successfully");
        userRepository.save(user);

        emailService.sendOtpMail(user.getEmail(), otp);
    }


    /**
     * Verify OTP
     */
    public boolean verifyOtp(String username, String otp) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if(user==null)
        	log.error("User not found");

        OtpToken token = otpRepository.findTopByUserIdOrderByCreatedTimeDesc(user.getId())
                .orElseThrow(() -> new RuntimeException("OTP not found"));
        if(token == null)
        	log.error("otp not found");

        if (token.getExpiry().isBefore(LocalDateTime.now())) {
        	log.error("otp has expired");
            throw new RuntimeException("OTP expired");
        }

        if (!token.getOtp().equals(otp)) {
        	log.error("invalid otp");
            throw new RuntimeException("Invalid OTP");
        }
        log.info("Otp verified successfully");
        return true;
    }


    /**
     * Resend OTP
     */
    public void resendOtp(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if(user==null)
        	log.error("User not found");

        OtpToken token = otpRepository
                .findTopByUserIdOrderByCreatedTimeDesc(user.getId())
                .orElseThrow(() -> new RuntimeException("OTP not found"));
        if(token == null)
        	log.error("otp not found");
        if (token.getResendCount() >= MAX_RESEND_ATTEMPTS) {
        	log.error("Max resend attempts reached");
            throw new RuntimeException("Max resend attempts reached");
        }

        long seconds = Duration.between(token.getCreatedTime(),
                LocalDateTime.now()).getSeconds();

        if (seconds < RESEND_WAIT_SECONDS) {
        	log.error("Please wait before requesting another OTP");
            throw new RuntimeException("Please wait before requesting another OTP");
        }

        String newOtp = String.format("%06d", random.nextInt(999999));

        token.setOtp(newOtp);
        token.setExpiry(LocalDateTime.now().plusMinutes(OTP_EXPIRY_MINUTES));
        token.setResendCount(token.getResendCount() + 1);
        token.setCreatedTime(LocalDateTime.now());
        log.info("Token saved successfully");
        otpRepository.save(token);

        emailService.sendOtpMail(user.getEmail(), newOtp);
    }

}