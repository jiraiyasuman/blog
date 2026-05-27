package com.blog_post.security;

import io.jsonwebtoken.Claims;

import jakarta.servlet.FilterChain;

import jakarta.servlet.ServletException;

import jakarta.servlet.http
.HttpServletRequest;

import jakarta.servlet.http
.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication
.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.authority
.SimpleGrantedAuthority;

import org.springframework.security.core.context
.SecurityContextHolder;

import org.springframework.security.web.authentication
.WebAuthenticationDetailsSource;

import org.springframework.stereotype.Component;

import org.springframework.web.filter
.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter
extends OncePerRequestFilter {

private final JwtUtil jwtUtil;

@Override
protected void doFilterInternal(

    HttpServletRequest request,

    HttpServletResponse response,

    FilterChain filterChain

) throws ServletException, IOException {

final String header =
        request.getHeader(
                SecurityConstants.HEADER
        );

if (header == null
        || !header.startsWith(
        SecurityConstants.TOKEN_PREFIX
)) {

    filterChain.doFilter(
            request,
            response
    );

    return;
}

String token =
        header.replace(
                SecurityConstants.TOKEN_PREFIX,
                ""
        );

if (!jwtUtil.validateToken(token)) {

    filterChain.doFilter(
            request,
            response
    );

    return;
}

Claims claims =
        jwtUtil.extractClaims(token);

Long userId =
        claims.get(
                "userId",
                Long.class
        );

String username =
        claims.getSubject();

List<String> roles =
        claims.get(
                "roles",
                List.class
        );

UsernamePasswordAuthenticationToken auth =
        new UsernamePasswordAuthenticationToken(

                username,

                null,

                roles.stream()
                        .map(
                                SimpleGrantedAuthority::new
                        )
                        .collect(Collectors.toList())
        );

auth.setDetails(
        new WebAuthenticationDetailsSource()
                .buildDetails(request)
);

SecurityContextHolder.getContext()
        .setAuthentication(auth);

UserContext.setUserId(userId);

log.info(
        "Authenticated User: {}",
        username
);

try {

    filterChain.doFilter(
            request,
            response
    );

} finally {

    UserContext.clear();
}
}
}
