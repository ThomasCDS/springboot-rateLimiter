package com.cds.service;

import com.cds.utils.RateLimiterUtil;
import com.cds.utils.RateLimiterUtil2;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service
public class Api1Service {

    @Resource
    private RateLimiterUtil rateLimiterUtil;

    @Resource
    private RateLimiterUtil2 rateLimiterUtil2;

    public boolean api1(String userId){
   //     return rateLimiterUtil2.allowRequest();
         return rateLimiterUtil.allowRequest(userId, "api1");
    }
}
