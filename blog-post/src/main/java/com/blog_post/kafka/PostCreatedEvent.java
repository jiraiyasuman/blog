package com.blog_post.kafka;

import java.time.LocalDateTime;

import com.blog_post.entity.PostWriteEntity.PostWriteEntityBuilder;

import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCreatedEvent {

	
	private Long postId;
	
	private Long userId;
	
	private String title;
	
	private String content;
	
	private LocalDateTime createdAt;

	public static PostWriteEntityBuilder builder() {
		// TODO Auto-generated method stub
		return null;
	}
}
