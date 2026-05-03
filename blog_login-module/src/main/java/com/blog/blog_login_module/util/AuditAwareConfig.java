package com.blog.blog_login_module.util;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

import com.blog.blog_login_module.entity.UserWrite;

public class AuditAwareConfig implements AuditorAware<Integer>{

	@Override
	public Optional<Integer> getCurrentAuditor() {
		UserWrite loggedUser = CommonUtil.getLoggedInUser();
		return Optional.empty();
	}
}
