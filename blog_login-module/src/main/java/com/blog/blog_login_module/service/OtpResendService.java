package com.blog.blog_login_module.service;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.blog_login_module.entity.OtpVerification;
import com.blog.blog_login_module.infra.EmailService;
import com.blog.blog_login_module.infra.SnowflakeIdGenerator;
import com.blog.blog_login_module.repository.OtpWriteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OtpResendService {

	private final OtpWriteRepository otpRepository;
	private final EmailService emailService;
	private final SnowflakeIdGenerator idGenerator;
	@Transactional
	public void resendOtp(String email) {
		OtpVerification latest = otpRepository.findTopByEmailOrderByExpiryTimeDesc(email);
		
		if(latest != null 
				&& latest.getResendAllowedAt().isAfter(LocalDateTime.now())) {
			throw new RuntimeException("Wait 60 seconds before resend");
		}
		OtpVerification otp = new OtpVerification();
		otp.setId(idGenerator.nextId());
		otp.setEmail(email);
		otp.setOtp(email);
		otp.setExpiryTime(LocalDateTime.now().plusMinutes(5));
		otp.setResendAllowedAt(LocalDateTime.now().plusSeconds(60));
		otp.setVerified(false);
		otpRepository.save(otp);
		emailService.sendOtpEmail(email, otp.getOtp());
	}
	private String generateotp() {
		return String.valueOf(new Random().nextInt(900000)+100000);
	}
}