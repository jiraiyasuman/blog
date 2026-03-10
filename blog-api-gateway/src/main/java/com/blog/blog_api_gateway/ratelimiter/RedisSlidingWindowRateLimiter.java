package com.blog.blog_api_gateway.ratelimiter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import org.springframework.data.domain.Range;
@Component
@RequiredArgsConstructor
public class RedisSlidingWindowRateLimiter {

    private final ReactiveStringRedisTemplate redisTemplate;

    private static final int MAX_REQUESTS = 10;
    private static final long WINDOW_SIZE = 60000;

    public Mono<Boolean> isAllowed(String key){

        long now = System.currentTimeMillis();

        String redisKey = "rate_limit:" + key;

        return redisTemplate.opsForZSet()
        		.removeRangeByScore(redisKey, Range.closed(0.0, (double) (now - WINDOW_SIZE)))

                .then(redisTemplate.opsForZSet()
                        .add(redisKey,String.valueOf(now),now))

                .then(redisTemplate.opsForZSet()
                        .size(redisKey))

                .map(size -> size <= MAX_REQUESTS);
    }

}