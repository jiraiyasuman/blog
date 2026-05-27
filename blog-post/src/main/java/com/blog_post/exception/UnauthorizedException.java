package com.blog_post.exception;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(
            String message
    ) {

        super(message);
    }
}
