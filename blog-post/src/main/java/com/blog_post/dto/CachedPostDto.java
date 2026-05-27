package com.blog_post.dto;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CachedPostDto  implements Serializable{

	private Long postId;
	
	private Long userId;
	
	private String title;
	
	private String comment;
	
	private Long likeCount;
	
	private Long commentCount;
	
	private LocalDateTime createdAt;
	
}
