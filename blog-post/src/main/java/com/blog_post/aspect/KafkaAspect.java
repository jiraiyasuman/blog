package com.blog_post.aspect;

import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.ProceedingJoinPoint;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class KafkaAspect {

    @Around(
            "@annotation(com.blog.postservice.aop.annotation.TrackKafka)"
    )
    public Object trackKafka(
            ProceedingJoinPoint joinPoint
    ) throws Throwable {

        log.info(
                "Kafka Publish Started: {}",
                joinPoint.getSignature()
                        .toShortString()
        );

        Object result =
                joinPoint.proceed();

        log.info(
                "Kafka Publish Completed: {}",
                joinPoint.getSignature()
                        .toShortString()
        );

        return result;
    }
}