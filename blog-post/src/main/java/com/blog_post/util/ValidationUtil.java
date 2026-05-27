package com.blog_post.util;

public class ValidationUtil {

	    private ValidationUtil() {}

	    public static void validatePostTitle(
	            String title
	    ) {

	        if (title == null
	                || title.isBlank()) {

	            throw new IllegalArgumentException(
	                    "Post title is required"
	            );
	        }

	        if (title.length() > 255) {

	            throw new IllegalArgumentException(
	                    "Post title exceeds limit"
	            );
	        }
	    }

	    public static void validateContent(
	            String content
	    ) {

	        if (content == null
	                || content.isBlank()) {

	            throw new IllegalArgumentException(
	                    "Content is required"
	            );
	        }
	    }
	}
