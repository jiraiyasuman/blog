package com.blog_post.util;

import org.springframework.stereotype.Component;

@Component
public class SnowflakeIdGenerator {

    private static final long EPOCH =
            1704067200000L;

    private static final long MACHINE_ID_BITS =
            10L;

    private static final long SEQUENCE_BITS =
            12L;

    private static final long MAX_MACHINE_ID =
            ~(-1L << MACHINE_ID_BITS);

    private static final long MAX_SEQUENCE =
            ~(-1L << SEQUENCE_BITS);

    private static final long MACHINE_ID_SHIFT =
            SEQUENCE_BITS;

    private static final long TIMESTAMP_SHIFT =
            SEQUENCE_BITS + MACHINE_ID_BITS;

    private final long machineId;

    private long sequence = 0L;

    private long lastTimestamp = -1L;

    public SnowflakeIdGenerator() {

        this.machineId = 1L;

        if (machineId > MAX_MACHINE_ID
                || machineId < 0) {

            throw new IllegalArgumentException(
                    "Invalid machine ID"
            );
        }
    }

    public synchronized long nextId() {

        long currentTimestamp =
                currentTimestamp();

        if (currentTimestamp < lastTimestamp) {

            throw new RuntimeException(
                    "Clock moved backwards"
            );
        }

        if (currentTimestamp == lastTimestamp) {

            sequence =
                    (sequence + 1)
                            & MAX_SEQUENCE;

            if (sequence == 0) {

                currentTimestamp =
                        waitNextMillis(
                                currentTimestamp
                        );
            }

        } else {

            sequence = 0;
        }

        lastTimestamp = currentTimestamp;

        return ((currentTimestamp - EPOCH)
                << TIMESTAMP_SHIFT)

                | (machineId << MACHINE_ID_SHIFT)

                | sequence;
    }

    private long waitNextMillis(
            long timestamp
    ) {

        while (timestamp == lastTimestamp) {

            timestamp = currentTimestamp();
        }

        return timestamp;
    }

    private long currentTimestamp() {

        return System.currentTimeMillis();
    }
}