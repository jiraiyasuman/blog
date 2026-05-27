package com.blog_post.config;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;

@Configuration
public class RateLimiterConfig {

	@Bean
	public Bucket bucket() {
		Bandwidth limit = Bandwidth.classic(20, 
				Refill.intervally(20, Duration.ofDays(1))
				);
		return Bucket.builder()
				.addLimit(limit)
				.build();
	}
}
