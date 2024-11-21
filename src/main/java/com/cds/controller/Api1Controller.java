package com.cds.controller;

import com.cds.service.Api1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api1")
public class Api1Controller {

    @Autowired
    private Api1Service api1Service;

    @GetMapping
    public ResponseEntity<String> api1() {
        String userId = "user1"; // 暂时写死用户ID，实际可从请求上下文中获取等
        if (api1Service.api1(userId)) {
            return new ResponseEntity<>("Api1 response", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Rate limit exceeded for api1", HttpStatus.TOO_MANY_REQUESTS);
        }
    }

}