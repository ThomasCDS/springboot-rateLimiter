package com.cds.service;

import com.cds.utils.RateLimiterUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service
public class Api2Service {

    @Resource
    private RateLimiterUtil rateLimiterUtil;

    public boolean api2(String userId){
        return rateLimiterUtil.allowRequest(userId, "api2");
    }
}