package com.blog_post.post.entity;

import java.time.LocalDateTime;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="post")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	@Column(name="user_id")
	private String userId;
	@Column(name="title")
	private String title;
	@Column(name="content")
	private String content;
	@Column(name="engagement_id")
	private String engagementId;
	@Column(name="created_at")
	private LocalDateTime createdAt;
	
	
}
