package com.blog_post.sharding;

public class ShardContext {

	private static final ThreadLocal<String> CURRENT_SHARD = new ThreadLocal<>();

	private ShardContext() {
	}

	public static void setShard(String shard) {

		CURRENT_SHARD.set(shard);
	}

	public static String getShard() {

		return CURRENT_SHARD.get();
	}

	public static void clear() {

		CURRENT_SHARD.remove();
	}
}
