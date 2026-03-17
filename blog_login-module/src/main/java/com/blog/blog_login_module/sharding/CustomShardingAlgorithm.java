package com.blog.blog_login_module.sharding;

import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CustomShardingAlgorithm implements StandardShardingAlgorithm<Long> {

    @Override
    public void init(Properties props) {
    }

    @Override
    public String getType() {
        return "CUSTOM";
    }

    // ✅ Precise sharding (for = queries)
    @Override
    public String doSharding(Collection<String> availableTargetNames,
                             PreciseShardingValue<Long> shardingValue) {

        long id = shardingValue.getValue();
        int shard = (int) (id % 2);

        for (String table : availableTargetNames) {
            if (table.endsWith(String.valueOf(shard))) {
                return table;
            }
        }

        throw new IllegalArgumentException("No shard found");
    }

    // ✅ Range sharding (for BETWEEN, >, < queries)
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames,
                                          RangeShardingValue<Long> shardingValue) {

        // simplest: return all tables
        return availableTargetNames;
    }
}