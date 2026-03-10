package com.blog.blog_api_gateway.ratelimiter;

import com.blog.blog_api_gateway.ratelimiter.RedisSlidingWindowRateLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;

@Component
@Order(3)
@RequiredArgsConstructor
public class RateLimiterFilter implements GlobalFilter {

    private final RedisSlidingWindowRateLimiter rateLimiter;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             GatewayFilterChain chain) {

        String clientIP =
                exchange.getRequest()
                        .getRemoteAddress()
                        .getAddress()
                        .getHostAddress();

        return rateLimiter.isAllowed(clientIP)
                .flatMap(allowed -> {

                    if(!allowed){

                        exchange.getResponse()
                                .setStatusCode(HttpStatus.TOO_MANY_REQUESTS);

                        return exchange.getResponse().setComplete();
                    }

                    return chain.filter(exchange);

                });
    }
}