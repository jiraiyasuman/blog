package com.blog_post.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer
        .ConsumerConfig;

import org.apache.kafka.clients.producer
        .ProducerConfig;

import org.apache.kafka.common.serialization
        .StringDeserializer;

import org.apache.kafka.common.serialization
        .StringSerializer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.kafka.config
        .ConcurrentKafkaListenerContainerFactory;

import org.springframework.kafka.core.*;

import org.springframework.kafka.listener
        .DefaultErrorHandler;

import org.springframework.kafka.support.serializer
        .JsonDeserializer;

import org.springframework.kafka.support.serializer
        .JsonSerializer;

import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Bean
    public ProducerFactory<String, Object>
    producerFactory() {

        Map<String, Object> config =
                new HashMap<>();

        config.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092"
        );

        config.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class
        );

        config.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class
        );

        config.put(
                ProducerConfig.ACKS_CONFIG,
                "all"
        );

        config.put(
                ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG,
                true
        );

        return new DefaultKafkaProducerFactory<>(
                config
        );
    }

    @Bean
    public KafkaTemplate<String, Object>
    kafkaTemplate() {

        return new KafkaTemplate<>(
                producerFactory()
        );
    }

    @Bean
    public ConsumerFactory<String, Object>
    consumerFactory() {

        JsonDeserializer<Object> deserializer =
                new JsonDeserializer<>();

        deserializer.addTrustedPackages("*");

        Map<String, Object> config =
                new HashMap<>();

        config.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092"
        );

        config.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                "post-service-group"
        );

        config.put(
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
                "earliest"
        );

        return new DefaultKafkaConsumerFactory<>(
                config,
                new StringDeserializer(),
                deserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory
            <String, Object>
    kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory
                <String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(
                consumerFactory()
        );

        factory.setCommonErrorHandler(
                errorHandler()
        );

        return factory;
    }

    @Bean
    public DefaultErrorHandler errorHandler() {

        return new DefaultErrorHandler(
                new FixedBackOff(1000L, 3)
        );
    }

    @Bean
    public NewTopic deadLetterTopic() {

        return new NewTopic(
                "dead-letter-topic",
                3,
                (short) 1
        );
    }
}