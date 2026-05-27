package com.blog_post.saga;

import org.springframework.stereotype.Component;

import com.blog_post.repository.PostWriteRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CompensationHandler {

	private  static PostWriteRepository postWriteRepository;
	
	public static void rollbackPost(Long postId) {
		log.error("Compensating transaction for Post: {}",postId);
		try {
			postWriteRepository.deleteById(postId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("Post Rollback Completed: {}", postId);
	}
	
}
