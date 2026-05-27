package com.blog_post.exception;

public class OutboxPublishException extends RuntimeException {

    public OutboxPublishException(
            String message
    ) {

        super(message);
    }

    public OutboxPublishException(
            String message,
            Throwable cause
    ) {

        super(message, cause);
    }
}