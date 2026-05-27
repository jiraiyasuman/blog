package com.blog_post.exception;

public enum ErrorCode {

	RESOURCE_NOT_FOUND,

    VALIDATION_ERROR,

    RATE_LIMIT_EXCEEDED,

    UNAUTHORIZED,

    KAFKA_PUBLISH_ERROR,

    SAGA_ERROR,

    CACHE_ERROR,

    SHARD_ERROR,

    INTERNAL_SERVER_ERROR
}
