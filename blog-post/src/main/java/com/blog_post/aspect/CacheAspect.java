package com.blog_post.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class CacheAspect {

    @AfterReturning(
            "execution(* com.blog.postservice.cache..*(..))"
    )
    public void monitorCache(
            JoinPoint joinPoint
    ) {

        log.info(
                "Cache Operation Executed: {}",
                joinPoint.getSignature()
                        .toShortString()
        );
    }
}
