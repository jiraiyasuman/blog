package com.blog.blog_login_module.service;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.blog_login_module.entity.OtpVerification;
import com.blog.blog_login_module.entity.UserRead;
import com.blog.blog_login_module.entity.UserWrite;
import com.blog.blog_login_module.infra.EmailService;
import com.blog.blog_login_module.infra.SnowflakeIdGenerator;
import com.blog.blog_login_module.repository.OtpWriteRepository;
import com.blog.blog_login_module.repository.UserReadRepository;
import com.blog.blog_login_module.repository.UserWriteRepository;

@Service
public class AuthCommandService {

    private final UserWriteRepository writeRepository;
    private final UserReadRepository readRepository;
    private final OtpWriteRepository otpRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final SnowflakeIdGenerator idGenerator;
    private final UserQueryCacheService cacheService;

    @Autowired
    public AuthCommandService(UserWriteRepository writeRepository,
                              UserReadRepository readRepository,
                              OtpWriteRepository otpRepository,
                              PasswordEncoder passwordEncoder,
                              JwtService jwtService,
                              EmailService emailService,
                              SnowflakeIdGenerator idGenerator,
                              UserQueryCacheService cacheService) {
        this.writeRepository = writeRepository;
        this.readRepository = readRepository;
        this.otpRepository = otpRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.emailService = emailService;
        this.idGenerator = idGenerator;
        this.cacheService = cacheService;
    }

    @Transactional
    public String register(String username, String email, String password) {

        if (writeRepository.findByUsername(username) != null) {
            throw new RuntimeException("Username already exists");
        }

        UserWrite user = new UserWrite();
        user.setId(idGenerator.nextId());
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setEnabled(false);
        user.setLocked(false);
        user.setFailedAttempts(0);

        saveAndSync(user);
        sendOtp(email);

        return "User registered. OTP sent.";
    }

    @Transactional
    public String login(String username, String password) {

        UserWrite user = writeRepository.findByUsername(username);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (user.isLocked()) {
            if (user.getLockTime() != null &&
                user.getLockTime().isBefore(LocalDateTime.now())) {

                user.setLocked(false);
                user.setFailedAttempts(0);
                user.setLockTime(null);
            } else {
                throw new RuntimeException("Account locked for 24 hours");
            }
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            processFailedLogin(user);
            throw new RuntimeException("Invalid credentials");
        }

        if (!user.isEnabled()) {
            throw new RuntimeException("OTP verification pending");
        }

        resetAttempts(user);

        return jwtService.generateToken(username);
    }

    public String logout(String token) {
        // TODO: Store token in Redis blacklist
        return "Logout successful";
    }

    @Transactional
    public void sendOtp(String email) {

        OtpVerification otp = new OtpVerification();
        otp.setId(idGenerator.nextId());
        otp.setEmail(email);
        otp.setOtp(generateOtp());
        otp.setExpiryTime(LocalDateTime.now().plusMinutes(5));
        otp.setResendAllowedAt(LocalDateTime.now().plusSeconds(60));
        otp.setVerified(false);

        otpRepository.save(otp);

        emailService.sendOtpEmail(email, otp.getOtp());
    }

    @Transactional
    public boolean verifyOtp(String email, String otpInput) {

        OtpVerification otp =
                otpRepository.findTopByEmailOrderByExpiryTimeDesc(email);

        if (otp == null ||
            otp.getExpiryTime().isBefore(LocalDateTime.now()) ||
            !otp.getOtp().equals(otpInput)) {
            return false;
        }

        otp.setVerified(true);
        otpRepository.save(otp);

        UserWrite user = writeRepository.findByEmail(email);
        user.setEnabled(true);

        saveAndSync(user);

        return true;
    }

    @Transactional
    public void resendOtp(String email) {

        OtpVerification latest =
                otpRepository.findTopByEmailOrderByExpiryTimeDesc(email);

        if (latest != null &&
            latest.getResendAllowedAt().isAfter(LocalDateTime.now())) {
            throw new RuntimeException("Wait 60 seconds before resend");
        }

        sendOtp(email);
    }

    @Transactional
    public void processFailedLogin(UserWrite user) {

        user.setFailedAttempts(user.getFailedAttempts() + 1);

        if (user.getFailedAttempts() >= 3) {
            user.setLocked(true);
            user.setLockTime(LocalDateTime.now().plusHours(24));
        }

        saveAndSync(user);
    }

    @Transactional
    public void resetAttempts(UserWrite user) {
        user.setFailedAttempts(0);
        user.setLocked(false);
        user.setLockTime(null);

        saveAndSync(user);
    }

    @Transactional
    public void updateUser(Long userId, String email) {

        UserWrite user = writeRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setEmail(email);

        saveAndSync(user);
    }

    @Transactional
    public void deleteUser(Long userId) {

        UserWrite user = writeRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        writeRepository.delete(user);
        readRepository.deleteById(userId);

        cacheService.evictUser(user.getUsername());
    }

    @Transactional
    public void saveAndSync(UserWrite user) {

        writeRepository.save(user);

        UserRead read = readRepository.findById(user.getId())
                .orElse(new UserRead());

        read.setId(user.getId());
        read.setUsername(user.getUsername());
        read.setEmail(user.getEmail());
        read.setEnabled(user.isEnabled());
        read.setLocked(user.isLocked());

        readRepository.save(read);

        cacheService.evictUser(user.getUsername());
    }

    private String generateOtp() {
        return String.valueOf(new Random().nextInt(900000) + 100000);
    }
}