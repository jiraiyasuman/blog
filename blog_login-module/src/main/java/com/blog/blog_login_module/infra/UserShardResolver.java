package com.blog.blog_login_module.infra;

import org.springframework.stereotype.Component;

@Component
public class UserShardResolver {

	
	public String resolveShard(String username) {
		int shard = Math.abs(username.hashCode()) % 2;
		return "shard_" + shard; 
	}
}
