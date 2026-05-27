package com.blog_post.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserDto {
	private Long userId;
	private String email;
	private String username;
	private List<String> roles;
	private List<String> postId;
}
