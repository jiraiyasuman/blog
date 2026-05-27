package com.blog_post.aspect;

import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.ProceedingJoinPoint;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class PerformanceAspect {

    @Around(
            "@annotation(com.blog.postservice.aop.annotation.TrackPerformance)"
    )
    public Object trackPerformance(
            ProceedingJoinPoint joinPoint
    ) throws Throwable {

        long start =
                System.currentTimeMillis();

        Object result =
                joinPoint.proceed();

        long executionTime =
                System.currentTimeMillis()
                        - start;

        log.info(
                "Execution Time {} : {} ms",
                joinPoint.getSignature()
                        .toShortString(),
                executionTime
        );

        return result;
    }
}