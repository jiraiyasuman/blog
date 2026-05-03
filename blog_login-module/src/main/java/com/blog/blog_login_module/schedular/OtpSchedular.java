package com.blog.blog_login_module.schedular;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.blog.blog_login_module.entity.OtpVerification;
import com.blog.blog_login_module.repository.OtpWriteRepository;

@Component
public class OtpSchedular {

	private OtpWriteRepository otpWriteRepository;

	@Autowired
	public OtpSchedular(OtpWriteRepository otpWriteRepository) {
		super();
		this.otpWriteRepository = otpWriteRepository;
	}
	@Scheduled(cron = "0 0 0 * * ?")
	public void deleteOtpScheduler() {
		LocalDateTime cutOffDate = LocalDateTime.now().minusDays(1);
		List<OtpVerification> delete = otpWriteRepository.findAllByIsDeletedAndDeletedOnBefore(true,cutOffDate);
	    otpWriteRepository.deleteAll(delete);  // deletes all the otp in the past 24 hours
	 
	}
	
}
