package com.cds.service;

import com.cds.utils.RateLimiterUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service
public class Api3Service {

    @Resource
    private RateLimiterUtil rateLimiterUtil;

    public boolean api3(String userId){
        return rateLimiterUtil.allowRequest(userId, "api3");
    }
}
