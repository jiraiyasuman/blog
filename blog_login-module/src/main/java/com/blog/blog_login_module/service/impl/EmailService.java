package com.blog.blog_login_module.service.impl;

import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

	private JavaMailSender javaMailSender;
	private ExecutorService executor;
	
	private Logger LOGGER = LoggerFactory.getLogger(getClass().getName());
	@Autowired
	public EmailService(JavaMailSender javaMailSender, ExecutorService executor) {
		super();
		this.javaMailSender = javaMailSender;
		this.executor = executor;
	}
	/**
	 * Send OTP mail asynchronously
	 */
	
	public void sendOtpMail(String email,String otp) {
		executor.submit(() -> {
			try {
				MimeMessage message = javaMailSender.createMimeMessage();
				
				MimeMessageHelper helper = new MimeMessageHelper(message, true);
				
				helper.setTo(email);
				
				helper.setSubject("Your login OTP code");
				
			    String htmlContent = buildOtpEmailTemplate(otp);
			    
			    helper.setText(htmlContent, true);
			    
			    javaMailSender.send(message);
			    
			    System.out.println("OTP email sent to "+ email);
			    LOGGER.info("OTP sent successfully ");
			    
			}catch(Exception e) {
				LOGGER.error("OTP could not be sent successfully"+e);
				System.err.println(e);
			}
		});
	}
	private String buildOtpEmailTemplate(String otp) {
		LOGGER.info("Email template sent successfully");
		return "<html>" +
                "<body style='font-family: Arial;'>" +
                "<div style='width:500px;margin:auto;border:1px solid #ddd;padding:20px'>" +
                "<h2 style='color:#333'>Login Verification</h2>" +
                "<p>Your One Time Password (OTP) is:</p>" +
                "<h1 style='color:#2E86C1'>" + otp + "</h1>" +
                "<p>This OTP is valid for <b>5 minutes</b>.</p>" +
                "<p>If you did not request this login, please ignore this email.</p>" +
                "<br>" +
                "<p>Regards,<br>Security Team</p>" +
                "</div>" +
                "</body>" +
                "</html>";
	}
	
	
	
	
}
