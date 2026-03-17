package com.blog.blog_login_module.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import io.jsonwebtoken.security.Keys;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    // Secret key for signing
    private final SecretKey SECRET_KEY =
            Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Access token validity (15 minutes)
    private static final long ACCESS_TOKEN_VALIDITY =
            1000 * 60 * 15;

    // Refresh token validity (7 days)
    private static final long REFRESH_TOKEN_VALIDITY =
            1000L * 60 * 60 * 24 * 7;



    /**
     * Generate Access Token
     */
    public String generateToken(String username) {

        return createToken(username, ACCESS_TOKEN_VALIDITY);
    }



    /**
     * Generate Refresh Token
     */
    public String generateRefreshToken(String username) {

        return createToken(username, REFRESH_TOKEN_VALIDITY);
    }



    /**
     * Core token creation logic
     */
    private String createToken(String subject, long validity) {

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(
                        new Date(System.currentTimeMillis() + validity)
                )
                .signWith(SECRET_KEY)
                .compact();
    }



    /**
     * Extract username from token
     */
    public String extractUsername(String token) {

        return extractClaim(token, Claims::getSubject);
    }



    /**
     * Extract expiration date
     */
    public Date extractExpiration(String token) {

        return extractClaim(token, Claims::getExpiration);
    }



    /**
     * Extract specific claim
     */
    public <T> T extractClaim(String token,
                              Function<Claims, T> claimsResolver) {

        final Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);
    }



    /**
     * Extract all claims
     */
    private Claims extractAllClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }



    /**
     * Check if token expired
     */
    private Boolean isTokenExpired(String token) {

        return extractExpiration(token).before(new Date());
    }



    /**
     * Validate token
     */
    public Boolean validateToken(String token,
                                 UserDetails userDetails) {

        final String username = extractUsername(token);

        return (username.equals(userDetails.getUsername())
                && !isTokenExpired(token));
    }

}