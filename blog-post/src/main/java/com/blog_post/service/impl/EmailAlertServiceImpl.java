package com.blog_post.service.impl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.blog_post.health.HealthStatusDto;
import com.blog_post.service.EmailAlertService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailAlertServiceImpl
        implements EmailAlertService {

    private final JavaMailSender
            mailSender;

    @Override
    public void sendAlert(
            HealthStatusDto status
    ) {

        SimpleMailMessage message =
                new SimpleMailMessage();

        message.setTo(
                "admin@blog.com"
        );

        message.setSubject(
                "SERVICE DOWN ALERT - "
                        + status.getServiceName()
        );

        message.setText(

                "Service: "
                        + status.getServiceName()

                        + "\nStatus: "
                        + status.getStatus()

                        + "\nMessage: "
                        + status.getMessage()

                        + "\nChecked At: "
                        + status.getCheckedAt()
        );

        mailSender.send(message);

        log.error(
                "Email Alert Sent for {}",
                status.getServiceName()
        );
    }

	
}