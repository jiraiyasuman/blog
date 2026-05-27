package com.blog_post.aspect;



import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.JoinPoint;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import org.springframework.stereotype.Component;

import com.blog_post.security.UserContext;

@Slf4j
@Aspect
@Component
public class SecurityAuditAspect {

@Before(
    "@annotation(auditAction)"
)
public void audit(

    JoinPoint joinPoint,

    AuditAction auditAction
) {

Long userId =
        UserContext.getUserId();

log.info(

        "AUDIT => user={}, action={}, method={}",

        userId,

        ((Aspect) auditAction).value(),

        joinPoint.getSignature()
                .toShortString()
);
}
}