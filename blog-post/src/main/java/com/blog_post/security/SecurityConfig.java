package com.blog_post.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
	private final JwtAuthenticationFilter
    jwtFilter;

private final JwtAuthenticationEntryPoint
    entryPoint;

@Bean
public SecurityFilterChain securityFilterChain(
    HttpSecurity http
) throws Exception {

http

        .csrf(csrf -> csrf.disable())

        .sessionManagement(session ->
                session.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS
                )
        )

        .exceptionHandling(exception ->
                exception.authenticationEntryPoint(
                        entryPoint
                )
        )

        .authorizeHttpRequests(auth ->

                auth

                        .requestMatchers(

                                "/swagger-ui/**",

                                "/v3/api-docs/**",

                                "/actuator/**"

                        ).permitAll()

                        .requestMatchers(
                                "/api/v1/admin/**"
                        ).hasRole("ADMIN")

                        .anyRequest()
                        .authenticated()
        )

        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
return http.build();
}
}
