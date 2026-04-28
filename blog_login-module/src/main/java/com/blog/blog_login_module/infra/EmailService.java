package com.blog.blog_login_module.infra;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class EmailService {

	private final JavaMailSender mailSender;
	private final ExecutorService executor = Executors.newCachedThreadPool();
	public EmailService(JavaMailSender mailSender) {
		super();
		this.mailSender = mailSender;
	}
	@CircuitBreaker(name = "emailService", fallbackMethod = "fallBackEmail")
	public void sendOtpEmail(String email, String otp) {
		executor.submit(() -> {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(email);
			message.setSubject("OTP Verification");
			message.setText("Your OTP is:"+otp);
			mailSender.send(message);
		});
	}
	
	public void fallbackEmail(String email, String otp, Exception ex) {
		System.out.println("Email failed, circuit breaker opened");
	}
	
}
