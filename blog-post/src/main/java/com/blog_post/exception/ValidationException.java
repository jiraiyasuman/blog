package com.blog_post.exception;

public class ValidationException extends RuntimeException {

    public ValidationException(
            String message
    ) {

        super(message);
    }
}
