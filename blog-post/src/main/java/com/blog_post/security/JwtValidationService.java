package com.blog_post.security;

import io.jsonwebtoken.Claims;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtValidationService {

    private  JwtUtil jwtUtil;

    public boolean validate(
            String token
    ) {

        return jwtUtil.validateToken(token);
    }

    public Long extractUserId(
            String token
    ) {

        Claims claims =
                jwtUtil.extractClaims(token);

        return claims.get(
                "userId",
                Long.class
        );
    }

    public String extractEmail(
            String token
    ) {

        return jwtUtil.extractUsername(token);
    }
}