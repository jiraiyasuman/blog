package com.blog_post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenValidationResponse {

	private Boolean valid;
	private Long userId;
	private String email;
}
