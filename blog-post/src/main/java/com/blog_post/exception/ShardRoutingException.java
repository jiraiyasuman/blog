package com.blog_post.exception;

public class ShardRoutingException extends RuntimeException {

    public ShardRoutingException(
            String message
    ) {

        super(message);
    }
}
