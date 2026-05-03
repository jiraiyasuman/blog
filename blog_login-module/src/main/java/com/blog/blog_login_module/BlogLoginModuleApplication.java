package com.blog.blog_login_module;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCaching
@EnableScheduling
@EnableJpaAuditing(auditorAwareRef = "auditAware")
public class BlogLoginModuleApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(BlogLoginModuleApplication.class, args);
	}

}
