package com.cds.utils;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.stereotype.Component;

/**
 *  方案二，使用令牌桶算法 来实现更平滑的流量控制
 */

@Component
public class RateLimiterUtil2 {

    private final RateLimiter rateLimiter;

    public RateLimiterUtil2() {
        // 每秒允许的请求数，这里假设每秒允许10000 / 60 次请求（根据每分钟10000次换算），可根据实际调整
        rateLimiter = RateLimiter.create(10000 / 60.0);
    }

    public boolean allowRequest() {
        return rateLimiter.tryAcquire();
    }
}
