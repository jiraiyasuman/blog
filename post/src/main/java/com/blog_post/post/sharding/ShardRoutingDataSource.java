package com.blog_post.post.sharding;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class ShardRoutingDataSource extends AbstractRoutingDataSource {

	private static final ThreadLocal<String> context = new ThreadLocal<>();
	
	public static void setShard(String shard) {
		context.set(shard);
	}
	public static void clear() {
		context.remove();
	}
	@Override
	protected Object determineCurrentLookupKey() {
		// TODO Auto-generated method stub
		return null;
	}
}
