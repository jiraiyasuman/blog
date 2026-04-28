package com.blog_post.post.sharding;

import org.springframework.stereotype.Component;

@Component
public class SnowflakeGenerator {

	private long sequence = 0L;
	private long lastTimeStamp = -1L;
	
	public synchronized long nextId() {
		long timestamp = System.currentTimeMillis();
		
		if(timestamp == lastTimeStamp) {
			sequence ++;
		}else {
			sequence = 0;
		}
		lastTimeStamp = timestamp;
		
		return (timestamp << 22) | sequence;
	}
}
