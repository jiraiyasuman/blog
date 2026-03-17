package com.blog.blog_login_module.service.impl;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

@Service
public class RateLimiterService {

	
	private StringRedisTemplate redisTemplate;

	@Autowired
	public RateLimiterService(StringRedisTemplate redisTemplate) {
		super();
		this.redisTemplate = redisTemplate;
	}
	
	private static final int MAX_REQUESTS = 5;
	private static final int WINDOW = 60;
	
	public boolean isAllowed(String key) {
		long now = System.currentTimeMillis();
		ZSetOperations<String, String> zset = redisTemplate.opsForZSet();
		zset.removeRangeByScore(key, 0,now-WINDOW*1000);
		Long count = zset.zCard(key);
		if(count != null && count>=MAX_REQUESTS) {
			return false;
		}
		zset.add(key,  String.valueOf(now),now);
		redisTemplate.expire(key, Duration.ofSeconds(WINDOW));
		return true;
	}
	
	public boolean allowRequest(String ip) {

        String key="rate:"+ip;

        Long count=redisTemplate.opsForValue().increment(key);

        if(count==1)
            redisTemplate.expire(key,10,TimeUnit.SECONDS);

        return count<=5;
    }
	
}
