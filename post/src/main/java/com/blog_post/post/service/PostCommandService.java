package com.blog_post.post.service;

import org.springframework.stereotype.Service;

import com.blog_post.post.dto.PostRequest;
import com.blog_post.post.dto.PostResponse;


public interface PostCommandService {

	public PostResponse createPost(PostRequest request, String userId);
}
