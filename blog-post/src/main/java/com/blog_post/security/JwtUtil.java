package com.blog_post.security;



import jakarta.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;


@Slf4j
@Component
public class JwtUtil {

	@Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private SecretKey key;

    @PostConstruct
    public void init() {

        this.key =
                Keys.hmacShaKeyFor(
                        secret.getBytes()
                );
    }

    public String generateToken(
            CustomUserDetails user
    ) {

        return Jwts.builder()

                .setSubject(user.getUsername())

                .claim(
                        "userId",
                        user.getUserId()
                )

                .claim(
                        "roles",
                        user.getRoles()
                )

                .setIssuedAt(
                        new Date()
                )

                .setExpiration(
                        new Date(
                                System.currentTimeMillis()
                                        + expiration
                        )
                )

                .signWith(
                        key,
                        SignatureAlgorithm.HS256
                )

                .compact();
    }

    public Claims extractClaims(
            String token
    ) {

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(
            String token
    ) {

        return extractClaims(token)
                .getSubject();
    }

    public Long extractUserId(
            String token
    ) {

        return extractClaims(token)
                .get("userId", Long.class);
    }

    public boolean validateToken(
            String token
    ) {

        try {

            extractClaims(token);

            return true;

        } catch (Exception ex) {

            log.error(
                    "JWT Validation Failed",
                    ex
            );

            return false;
        }
    }
	
}
