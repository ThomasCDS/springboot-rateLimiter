package com.cds.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RateLimiterUtil {

    private final RedisTemplate<String, Object> redisTemplate;

    public RateLimiterUtil(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean allowRequest(String userId, String apiName) {
        String key = userId + ":" + apiName; //  key :     user1:api1
        Long count = redisTemplate.opsForValue().increment(key, 1);
        if (count == 1) {
            // 设置过期时间为1分钟（60秒），对应每分钟的流量控制
            redisTemplate.expire(key, 60, TimeUnit.SECONDS);
        }
        return count <= 10000; // 这里假设每分钟允许10000次请求，可配置化
    }
}
