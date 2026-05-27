package com.blog_post.common;

import java.io.Serializable;
import java.time.Instant;

public abstract class BaseDto  implements Serializable {

    private Instant createdAt;

    private Instant updatedAt;
}