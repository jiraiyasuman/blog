package com.blog.blog_login_module.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEvent {

	private Long id;
	private String email;
	private String username;
	private boolean enabled;
	private boolean locked;
	private String eventType;
	
}
