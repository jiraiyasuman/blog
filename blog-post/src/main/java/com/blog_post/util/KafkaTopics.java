package com.blog_post.util;

public class KafkaTopics {
	

	    private KafkaTopics() {}

	    public static final String
	            POST_CREATED_TOPIC =
	            "post-created-topic";

	    public static final String
	            POST_UPDATED_TOPIC =
	            "post-updated-topic";

	    public static final String
	            POST_DELETED_TOPIC =
	            "post-deleted-topic";

	    public static final String
	            LIKE_CREATED_TOPIC =
	            "like-created-topic";

	    public static final String
	            LIKE_DELETED_TOPIC =
	            "like-deleted-topic";

	    public static final String
	            COMMENT_CREATED_TOPIC =
	            "comment-created-topic";

	    public static final String
	            COMMENT_DELETED_TOPIC =
	            "comment-deleted-topic";

	    public static final String
	            READ_SYNC_TOPIC =
	            "post-read-sync-topic";

	    public static final String
	            SAGA_TOPIC =
	            "saga-topic";

	    public static final String
	            DLQ_TOPIC =
	            "dead-letter-topic";
	}