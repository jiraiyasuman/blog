package com.blog.blog_login_module.service;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenBlacklistService {

    private final StringRedisTemplate redisTemplate;

    private static final String PREFIX = "BLACKLIST_";

    // Add token to blacklist
    public void blacklistToken(String token, long expiryMillis) {

        long ttl = expiryMillis - System.currentTimeMillis();

        if (ttl > 0) {
            redisTemplate.opsForValue()
                    .set(PREFIX + token, "BLACKLISTED", Duration.ofMillis(ttl));
        }
    }

    // Check if token is blacklisted
    public boolean isBlacklisted(String token) {
        return redisTemplate.hasKey(PREFIX + token);
    }
}