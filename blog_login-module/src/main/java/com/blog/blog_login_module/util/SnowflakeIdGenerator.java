package com.blog.blog_login_module.util;

import org.springframework.stereotype.Component;

@Component
public class SnowflakeIdGenerator {

	private final long nodeId = 1;
	private final long epoch = 1672531200000L;
	private long lastTimeStamp = -1L;
	private long sequence = 0L;
	
	public synchronized long nextId() {
		long currentTimeStamp = System.currentTimeMillis();
		if(currentTimeStamp == lastTimeStamp) {
			sequence = (sequence+1) & 4095;
		}else {
			sequence = 0;
		}
		lastTimeStamp = currentTimeStamp;
		
		return ((currentTimeStamp-epoch)<<22)|(nodeId<<12)|sequence;
	}
	
	
}
