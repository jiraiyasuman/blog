package com.blog.blog_login_module.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.blog_login_module.entity.OtpVerification;
import com.blog.blog_login_module.entity.UserRead;
import com.blog.blog_login_module.entity.UserWrite;
import com.blog.blog_login_module.repository.OtpWriteRepository;
import com.blog.blog_login_module.repository.UserReadRepository;
import com.blog.blog_login_module.repository.UserWriteRepository;

import lombok.RequiredArgsConstructor;

@Service
public class OtpVerificationService {

	private final OtpWriteRepository otpWriteRepository;
	private final UserWriteRepository writeRepository;
	private final UserReadRepository readRepository;
	@Autowired
	public OtpVerificationService(OtpWriteRepository otpWriteRepository, UserWriteRepository writeRepository,
			UserReadRepository readRepository) {
		super();
		this.otpWriteRepository = otpWriteRepository;
		this.writeRepository = writeRepository;
		this.readRepository = readRepository;
	}
	@Transactional
	public boolean verifyOtp(String email, String otpInput) {
		OtpVerification otp = otpWriteRepository.findTopByEmailOrderByExpiryTimeDesc(email);
		
		if(otp == null)
			return false;
		
		if(otp.getExpiryTime().isBefore(LocalDateTime.now()))
			return false;
		if(!otp.getOtp().equals(otpInput))
			return false;
		otp.setVerified(true);
		otpWriteRepository.save(otp);
		
		UserWrite user = writeRepository.findByEmail(email);
		user.setEnabled(true);
		
		writeRepository.save(user);
		syncReadModel(user);
		return true;
		
	}
	
	private void syncReadModel(UserWrite user) {
	UserRead read = readRepository.findById(user.getId()).orElse(new UserRead());
	read.setId(user.getId());
	read.setUsername(user.getUsername());
	read.setEmail(user.getEmail());
	read.setEnabled(true);
	read.setLocked(user.isLocked());
	readRepository.save(read);
	}
}
