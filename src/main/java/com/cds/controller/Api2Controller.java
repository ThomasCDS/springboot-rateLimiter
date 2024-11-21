package com.cds.controller;

import com.cds.service.Api2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api2")
public class Api2Controller {

    @Autowired
    private Api2Service api2Service;

    @PostMapping
    public ResponseEntity<String> api1() {
        String userId = "user1"; // 暂时写死用户ID，实际可从请求上下文中获取等
        if (api2Service.api2(userId)) {
            return new ResponseEntity<>("Api2 response", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Rate limit exceeded for api2", HttpStatus.TOO_MANY_REQUESTS);
        }
    }

}