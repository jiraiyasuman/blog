package com.blog_post.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private DateUtil() {}

    public static final String DEFAULT_PATTERN =
            "yyyy-MM-dd HH:mm:ss";

    public static String formatInstant(
            Instant instant
    ) {

        return LocalDateTime.ofInstant(
                        instant,
                        ZoneId.systemDefault()
                )
                .format(
                        DateTimeFormatter.ofPattern(
                                DEFAULT_PATTERN
                        )
                );
    }

    public static Instant now() {

        return Instant.now();
    }

    public static boolean isExpired(
            Instant expiry
    ) {

        return Instant.now().isAfter(expiry);
    }
}