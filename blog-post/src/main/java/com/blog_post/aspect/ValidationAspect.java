package com.blog_post.aspect;

import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.JoinPoint;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ValidationAspect {

    @Before(
            "@annotation(com.blog.postservice.aop.annotation.ValidateRequest)"
    )
    public void validate(
            JoinPoint joinPoint
    ) {

        log.info(
                "Validation Triggered: {}",
                joinPoint.getSignature()
                        .toShortString()
        );
    }
}
