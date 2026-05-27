package com.blog_post.aspect;

import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.ProceedingJoinPoint;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class SagaAspect {

    @Around(
            "@annotation(com.blog.postservice.aop.annotation.SagaTracked)"
    )
    public Object trackSaga(
            ProceedingJoinPoint joinPoint
    ) throws Throwable {

        log.info(
                "Saga Started: {}",
                joinPoint.getSignature()
                        .toShortString()
        );

        try {

            Object result =
                    joinPoint.proceed();

            log.info(
                    "Saga Completed: {}",
                    joinPoint.getSignature()
                            .toShortString()
            );

            return result;

        } catch (Exception ex) {

            log.error(
                    "Saga Failed: {}",
                    joinPoint.getSignature()
                            .toShortString(),
                    ex
            );

            throw ex;
        }
    }
}
