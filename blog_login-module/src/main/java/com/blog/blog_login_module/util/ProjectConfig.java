package com.blog.blog_login_module.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
@Configuration
public class ProjectConfig {

	public AuditorAware<Integer> auditorAware(){
		return new AuditAwareConfig();
	}
}
