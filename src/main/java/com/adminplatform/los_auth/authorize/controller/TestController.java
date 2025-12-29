package com.adminplatform.los_auth.authorize.controller;

import com.adminplatform.los_auth.authorize.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController

@RequestMapping("/test")
public class TestController {
    public TestController() {
        System.out.println("✅ TestController initialized");
    }

    @GetMapping
    public String hello() {
        return "Hello from test";
    }
}