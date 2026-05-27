package com.blog_post.sharding;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class ShardAspect {

    @Around("@annotation(sharded)")
    public Object routeShard(
            ProceedingJoinPoint joinPoint,
            Sharded sharded
    ) throws Throwable {

        Object[] args = joinPoint.getArgs();

        Long userId = extractUserId(args);

        if (userId != null) {

            ShardResolver.resolveShard(userId);

            log.info(
                    "Routing request for user {}",
                    (Object) userId
            );
        }

        try {

            return joinPoint.proceed();

        } finally {

            ShardContext.clear();
        }
    }

    private Long extractUserId(
            Object[] args
    ) {

        for (Object arg : args) {

            if (arg instanceof Long value) {
                return value;
            }
        }

        return null;
    }
}