package com.blog_post.sharding;

public class ShardUtil {

	private ShardUtil() {
		
	}
	
	public static String shardKey(
			long userId
			) {
		long value = userId%3;
		if(value == 0) {
			return "SHARD_1";
		}else if(value == 1) {
			return "SHARD_2";
		}else {
			return "SHARD_3";
		}
	}
}
