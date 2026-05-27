package com.blog_post.sharding;


public class UserShardStrategy {

    private static final int TOTAL_SHARDS = 3;

    private UserShardStrategy() {}

    public static String determineShard(
            Long userId
    ) {

        int shard =
                (int) (userId % TOTAL_SHARDS);

        return switch (shard) {

            case 0 -> RoutingConstants.SHARD_1;

            case 1 -> RoutingConstants.SHARD_2;

            default -> RoutingConstants.SHARD_3;
        };
    }
}