package com.blog_post.aspect;

import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.ProceedingJoinPoint;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Around(
            "@annotation(com.blog.postservice.aop.annotation.LogExecution)"
    )
    public Object logExecution(
            ProceedingJoinPoint joinPoint
    ) throws Throwable {

        String className =
                joinPoint.getTarget()
                        .getClass()
                        .getSimpleName();

        String method =
                joinPoint.getSignature()
                        .getName();

        log.info(
                "Entering {}.{}",
                className,
                method
        );

        try {

            Object result =
                    joinPoint.proceed();

            log.info(
                    "Completed {}.{}",
                    className,
                    method
            );

            return result;

        } catch (Exception ex) {

            log.error(
                    "Error in {}.{}",
                    className,
                    method,
                    ex
            );

            throw ex;
        }
    }
}