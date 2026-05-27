package com.blog_post.aspect;

import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;

import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ExceptionAspect {

    @AfterThrowing(

            pointcut =
                    "execution(* com.blog.postservice..*(..))",

            throwing = "ex"
    )
    public void logException(
            Exception ex
    ) {

        log.error(
                "Unhandled Exception Captured",
                ex
        );
    }
}