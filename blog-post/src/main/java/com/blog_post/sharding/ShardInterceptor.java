package com.blog_post.sharding;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ShardInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {

        String userIdHeader =
                request.getHeader("X-USER-ID");

        if (userIdHeader != null) {

            Long userId =
                    Long.parseLong(userIdHeader);

            ShardResolver.resolveShard(userId);

            log.info(
                    "Shard resolved for user: {}",
                    userId
            );
        }

        return true;
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex
    ) throws Exception {

        ShardContext.clear();
    }
}