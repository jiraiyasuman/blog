package com.blog_post.sharding;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ShardResolver {

    private ShardResolver() {}

    public static void resolveShard(
            Long userId
    ) {

        String shard =
                UserShardStrategy
                        .determineShard(userId);

        ShardContext.setShard(shard);

        log.info(
                "Shard Resolved: {} for User: {}",
                shard,
                userId
        );
    }

    public static void useReadDb() {

        ShardContext.setShard(
                RoutingConstants.READ_DB
        );

        log.info(
                "Using READ_DB"
        );
    }
}