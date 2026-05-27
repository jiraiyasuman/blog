package com.blog_post.aspect;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.blog_post.exception.RateLimitExceededException;
import com.blog_post.security.UserContext;

import io.github.bucket4j.Bucket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class RateLimitAspect {

    private final Map<Long, Bucket>
            userBuckets =
            new ConcurrentHashMap<>();

    @Around(
            "@annotation(com.blog.postservice.aop.annotation.RateLimited)"
    )
    public Object rateLimit(
            ProceedingJoinPoint joinPoint
    ) throws Throwable {

        Long userId =
                UserContext.getUserId();

        Bucket bucket =
                userBuckets.computeIfAbsent(

                        userId,

                        id -> Bucket.builder()

                                .addLimit(limit ->

                                        limit.capacity(20)

                                                .refillGreedy(
                                                        20,
                                                        java.time.Duration
                                                                .ofDays(1)
                                                )
                                )

                                .build()
                );

        if (!bucket.tryConsume(1)) {

            throw new RateLimitExceededException(
                    "Daily post limit exceeded"
            );
        }

        log.info(
                "Rate Limit Consumed for User: {}",
                userId
        );

        return joinPoint.proceed();
    }
}