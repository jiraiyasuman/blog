package com.blog_post.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtil {

    private static final ObjectMapper
            OBJECT_MAPPER =
            new ObjectMapper();

    static {

        OBJECT_MAPPER.registerModule(
                new JavaTimeModule()
        );
    }

    private JsonUtil() {}

    public static String toJson(
            Object object
    ) {

        try {

            return OBJECT_MAPPER
                    .writeValueAsString(object);

        } catch (JsonProcessingException ex) {

            log.error(
                    "JSON Serialization Error",
                    ex
            );

            throw new RuntimeException(ex);
        }
    }

    public static <T> T fromJson(

            String json,

            Class<T> clazz

    ) {

        try {

            return OBJECT_MAPPER.readValue(
                    json,
                    clazz
            );

        } catch (Exception ex) {

            log.error(
                    "JSON Deserialization Error",
                    ex
            );

            throw new RuntimeException(ex);
        }
    }
}