package com.blog_post.post.sharding;

import org.springframework.stereotype.Component;

@Component
public class ShardResolver {

	public String resolve(long id) {
		return "shard_"+(id%2);
	}
}
