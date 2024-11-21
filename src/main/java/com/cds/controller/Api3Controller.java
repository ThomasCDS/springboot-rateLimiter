package com.cds.controller;

import com.cds.service.Api3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api3")
public class Api3Controller {

    @Autowired
    private Api3Service api3Service;

    @PutMapping
    public ResponseEntity<String> api3() {
        String userId = "user3"; // 暂时写死用户ID，实际可从请求上下文中获取等
        if (api3Service.api3(userId)) {
            return new ResponseEntity<>("Api3 response", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Rate limit exceeded for api3", HttpStatus.TOO_MANY_REQUESTS);
        }
    }

}