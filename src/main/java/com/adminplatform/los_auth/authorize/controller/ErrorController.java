package com.adminplatform.los_auth.authorize.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @GetMapping("/error")
    public String handleError(HttpServletRequest request) {
        System.out.println("⚠️ Error triggered for: " + request.getRequestURI());
        return "forward:/index.html";
    }
}
