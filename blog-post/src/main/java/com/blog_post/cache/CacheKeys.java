package com.blog_post.cache;

public class CacheKeys {

	private CacheKeys() {}
	public static final String POST= "post";
	
	public static final String FEED = "Feed";
	public static final String USER_FEED = "user_feed";
	
	public static final String TRENDING = "trending";
	
	public static final String POST_LIKES = "post_likes";
	
	public static final String POST_COMMENT = "post_comments";
	
	public static final String PAGE ="page";
	
	public static String postkey(Long postId) {
	      return POST + postId;	
	}
	
	public static String feedKey(int page) {
	      return FEED + PAGE + page;  	
	}
	
	public static String userfeedkey( Long userId, int page ) {
		return USER_FEED + userId+ PAGE + page;
	}
	
	public static String trendingKey() {
		return TRENDING + "global";
	}
}
