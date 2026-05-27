package com.blog_post.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic postCreatedTopic() {

        return TopicBuilder
                .name("post-created-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic postUpdatedTopic() {

        return TopicBuilder
                .name("post-updated-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic postDeletedTopic() {

        return TopicBuilder
                .name("post-deleted-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic likeCreatedTopic() {

        return TopicBuilder
                .name("like-created-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic commentCreatedTopic() {

        return TopicBuilder
                .name("comment-created-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic readSyncTopic() {

        return TopicBuilder
                .name("post-read-sync-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic sagaTopic() {

        return TopicBuilder
                .name("saga-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic healthAlertTopic() {

        return TopicBuilder
                .name("health-alert-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic deadLetterTopic() {

        return TopicBuilder
                .name("dead-letter-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }
}