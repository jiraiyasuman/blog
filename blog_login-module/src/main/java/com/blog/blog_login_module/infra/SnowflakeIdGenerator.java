package com.blog.blog_login_module.infra;

import org.springframework.stereotype.Component;

@Component
public class SnowflakeIdGenerator {

	private long machineId =  1;
	private long sequence = 0L;
	private long lastTimeStamp = -1L;
	
	
	// synchronized method
	
	public synchronized long nextId() {
		long current = System.currentTimeMillis();
		if(current == lastTimeStamp) {
			sequence = (sequence +1) & 4095;
		}else {
			sequence = 0;
		}
		lastTimeStamp = current;
		
		return ((current - 1672531200000L) << 22) | (machineId << 12) | sequence;
	}
}
