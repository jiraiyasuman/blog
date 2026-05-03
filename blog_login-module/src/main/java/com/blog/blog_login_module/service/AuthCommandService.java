package com.blog.blog_login_module.service;

import java.time.LocalDateTime;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.blog.blog_login_module.dto.UserEvent;
import com.blog.blog_login_module.entity.OtpVerification;
import com.blog.blog_login_module.entity.OutboxEvent;
import com.blog.blog_login_module.entity.UserWrite;
import com.blog.blog_login_module.error.AccountLockedException;
import com.blog.blog_login_module.error.ExistDataException;
import com.blog.blog_login_module.error.InvalidCredentialsError;
import com.blog.blog_login_module.error.ResourceNotFoundException;
import com.blog.blog_login_module.infra.EmailService;
import com.blog.blog_login_module.infra.SnowflakeIdGenerator;
import com.blog.blog_login_module.repository.OtpWriteRepository;
import com.blog.blog_login_module.repository.OutboxRepository;
import com.blog.blog_login_module.repository.UserWriteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class AuthCommandService {

	private final TokenBlacklistService blacklistService;
    private final UserWriteRepository writeRepository;
    private final OtpWriteRepository otpRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final SnowflakeIdGenerator idGenerator;
    private final UserQueryCacheService cacheService;
    private final OutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;
    private final Logger LOGGER = LoggerFactory.getLogger(AuthCommandService.class);
    // ================= REGISTER =================
    @Transactional
    public String register(String username, String email, String password) {

    	LOGGER.info("AuthCommandService : register() {} : has started executing");
    	if (writeRepository.findByUsername(username) != null) {
    		LOGGER.error("AuthCommandService : register() {} : Record already existing");
            throw new ExistDataException("Username already exists");
        }
        UserWrite user = new UserWrite();
        user.setId(idGenerator.nextId());
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setEnabled(false);
        user.setLocked(false);
        user.setFailedAttempts(0);
        LOGGER.info("AuthCommandService : register() {} : register data sent for kafka message broking and outbox details");
        saveUserWithOutbox(user, "CREATE");
        LOGGER.info("AuthCommandService : register() {} : registration details will be sent to registered email id");
        sendOtp(email);
        LOGGER.info("AuthCommandService : register() {} : has finished executing successfully");
        return "User registered. OTP sent.";
    }

    // ================= LOGIN =================
    @Transactional
    public String login(String username, String password) throws ResourceNotFoundException {
    	
    	LOGGER.info("AuthCommandService : login() {} : has started executing ");
        UserWrite user = writeRepository.findByUsername(username);
        
        if (user == null) {
        	LOGGER.error("AuthCommandService : login() {} : User not found exception");
            throw new ResourceNotFoundException("User not found");
        }
        
        if (user.isLocked()) {
            if (user.getLockTime() != null &&
                user.getLockTime().isBefore(LocalDateTime.now())) {

                user.setLocked(false);
                user.setFailedAttempts(0);
                user.setLockTime(null);
                LOGGER.info("AuthCommandService : login() {} : login details sent to kafka message and outbox for saving");
                saveUserWithOutbox(user, "UPDATE");
            } else {
            	LOGGER.error("AuthCommandService : login() {} : Account locked exception thrown");
                throw new AccountLockedException("Account locked for 24 hours");
            }
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            processFailedLogin(user);
            LOGGER.error("AuthCommandService : login() {} : Invalid Credentials exception thrown");
            throw new InvalidCredentialsError("Invalid credentials");
        }

        if (!user.isEnabled()) {
        	LOGGER.error("AuthCommandService : login() {} : Otp verification pending exception thrown");
            throw new RuntimeException("OTP verification pending");
        }

        resetAttempts(user);
        LOGGER.info("AuthCommandService : login() {} : Login() has completed execution successfully");
        return jwtService.generateToken(username);
    }

    // ================= LOGOUT =================
    public String logout(String token) {
    	
    	LOGGER.info("AuthCommandService : logout() {} : logout() has started execution");
    	long expiry = jwtService.extractExpirationMillis(token);
    	
        blacklistService.blacklistToken(token, expiry);
        LOGGER.info("AuthCommandService : logout() {} : logout() has finished execution");
        return "Logout successful";
    }
    // ================= SEND OTP =================
    @Transactional
    public void sendOtp(String email) {
    	LOGGER.info("AuthCommandService : sendOtp() {} : sendOtp() has started execution");
        OtpVerification otp = new OtpVerification();
        otp.setId(idGenerator.nextId());
        otp.setEmail(email);
        otp.setOtp(generateOtp());
        otp.setExpiryTime(LocalDateTime.now().plusMinutes(5));
        otp.setResendAllowedAt(LocalDateTime.now().plusSeconds(60));
        otp.setVerified(false);
        
        otpRepository.save(otp);
        LOGGER.info("AuthCommandService : sendOtp() {} : sendOtp() has ended execution successfully");
        emailService.sendOtpEmail(email, otp.getOtp());
    }

    // ================= VERIFY OTP =================
    @Transactional
    public boolean verifyOtp(String email, String otpInput) {
    	LOGGER.info("AuthCommandService : verifyOtp() {} : verifyOtp() has started execution");
        OtpVerification otp =
                otpRepository.findTopByEmailOrderByExpiryTimeDesc(email);

        if (otp == null ||
            otp.getExpiryTime().isBefore(LocalDateTime.now()) ||
            !otp.getOtp().equals(otpInput)) {
        	LOGGER.error("AuthCommandService : verifyOtp() {} : otp verification is unsuccessful");
            return false;
        }

        otp.setVerified(true);
        LOGGER.info("AuthCommandService : verifyOtp() {} : otp verification is successful");
        otpRepository.save(otp);
        
        UserWrite user = writeRepository.findByEmail(email);
        user.setEnabled(true);
        LOGGER.info("AuthCommandService : verifyOtp() {} : User information sent to kafka message broker after successful otp verification");
        saveUserWithOutbox(user, "UPDATE");
        LOGGER.info("AuthCommandService : verifyOtp() {} : verifyOtp() has started execution");
        return true;
    }

    // ================= RESEND OTP =================
    @Transactional
    public void resendOtp(String email) {
    	LOGGER.info("AuthCommandService : resendOtp() {} : resendOtp() has started execution");
        OtpVerification latest =
                otpRepository.findTopByEmailOrderByExpiryTimeDesc(email);
        
        if (latest != null &&
            latest.getResendAllowedAt().isAfter(LocalDateTime.now())) {
        	LOGGER.error("AuthCommandService : resendOtp() {} : Resend OTP exception has occurred");
            throw new RuntimeException("Wait 60 seconds before resend");
        }
        LOGGER.info("AuthCommandService : resendOtp() {} : resendOtp() has finished execution");
        sendOtp(email);
    }

    // ================= FAILED LOGIN =================
    @Transactional
    public void processFailedLogin(UserWrite user) {
    	LOGGER.info("AuthCommandService : processFailedLogin() {} : processFailedLogin() has started execution");
        user.setFailedAttempts(user.getFailedAttempts() + 1);

        if (user.getFailedAttempts() >= 3) {
            user.setLocked(true);
            user.setLockTime(LocalDateTime.now().plusHours(24));
            LOGGER.info("AuthCommandService : processFailedLogin() {} : Account successfully locked for 24 hours");
        }
        LOGGER.info("AuthCommandService : processFailedLogin() {} : processFailedLogin() has finished execution successfully");
        saveUserWithOutbox(user, "UPDATE");
    }

    // ================= RESET ATTEMPTS =================
    @Transactional
    public void resetAttempts(UserWrite user) {
    	LOGGER.info("AuthCommandService : resetAttempts() {} : resetAttempts() has started execution");
        user.setFailedAttempts(0);
        user.setLocked(false);
        user.setLockTime(null);
        
        saveUserWithOutbox(user, "UPDATE");
        LOGGER.info("AuthCommandService : resetAttempts() {} : resetAttempts() has successfully execution successfully");
    }

    // ================= UPDATE USER =================
    @Transactional
    public void updateUser(Long userId, String email) {
    	LOGGER.info("AuthCommandService : updateUser() {} : updateUser() has started execution");
        UserWrite user = writeRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setEmail(email);

        saveUserWithOutbox(user, "UPDATE");
        LOGGER.info("AuthCommandService : updateUser() {} : updateUser() has finished execution successfully");
    }

    // ================= DELETE USER =================
    @Transactional
    public void deleteUser(Long userId) {
    	LOGGER.info("AuthCommandService : deleteUser() {} : deleteUser() has started execution successfully");
        UserWrite user = writeRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        writeRepository.delete(user);
        LOGGER.info("AuthCommandService : deleteUser() {} : deleteUser() has completed execution successfully");
        // publish delete event   
        saveUserWithOutbox(user, "DELETE");
        LOGGER.info("AuthCommandService : deleteUser() {} : User information sent to kafka message broker after successful user deletion");
    }

    // ================= CORE: OUTBOX METHOD =================
    @Transactional
    public void saveUserWithOutbox(UserWrite user, String eventType) {
    	LOGGER.info("AuthCommandService : saveUserWithOutbox() {} : saveUserWithOutbox() has started execution ");
        writeRepository.save(user);

        try {
            UserEvent event = UserEvent.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .username(user.getUsername())
                    .enabled(user.isEnabled())
                    .locked(user.isLocked())
                    .eventType(eventType)
                    .build();

            String payload = objectMapper.writeValueAsString(event);

            OutboxEvent outbox = OutboxEvent.builder()
                    .aggregateType("USER")
                    .aggregateId(user.getId())
                    .eventType(eventType)
                    .payload(payload)
                    .processed(false)
                    .createdAt(LocalDateTime.now())
                    .build();

            outboxRepository.save(outbox);
            LOGGER.info("AuthCommandService : saveUserWithOutbox() {} : saveUserWithOutbox() has finished execution successfully");

        } catch (Exception e) {
        	LOGGER.info("AuthCommandService : saveUserWithOutbox() {} : Exception thrown in catch clause "+e.getMessage());
            throw new RuntimeException("Outbox event creation failed", e);
        }
        LOGGER.info("AuthCommandService : saveUserWithOutbox() {} : data sent to evictUser to remove deleted user data from cache ");
        cacheService.evictUser(user.getUsername());
    }

    // ================= OTP GENERATOR =================
    private String generateOtp() {
    	LOGGER.info("AuthCommandService : generateOtp() {} : generateOtp() has started executing");
    	LOGGER.info("AuthCommandService : generateOtp() {} : generateOtp() has finished executing successfully");
        return String.valueOf(new Random().nextInt(90000) + 10000);
    }
}