package com.blog_post.resilience;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikeResponseDto {

	
	private Long postId;
	private Long likeCount;
	private Boolean liked;
}
