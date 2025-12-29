package com.adminplatform.los_auth.authorize.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontendController {

    // ✅ Match /{tenantSlug}/login
    @GetMapping("/{tenantSlug}/login")
    public String forwardLogin() {
        return "forward:/index.html";
    }

    // ✅ Match /{tenantSlug}/register
    @GetMapping("/{tenantSlug}/register")
    public String forwardRegister() {
        return "forward:/index.html";
    }

    // ✅ Match /{tenantSlug}/forgot-password
    @GetMapping("/{tenantSlug}/forgot-password")
    public String forwardForgotPassword() {
        return "forward:/index.html";
    }

    // ✅ Match /{tenantSlug}/reset-password
    @GetMapping("/{tenantSlug}/reset-password")
    public String forwardResetPassword() {
        return "forward:/index.html";
    }

    // ✅ Match /{tenantSlug}/{path}
    @GetMapping("/{tenantSlug}/{path}")
    public String forwardTwoSegmentRoutes() {
        return "forward:/index.html";
    }

    // ✅ Match /{tenantSlug}/{path1}/{path2}
    @GetMapping("/{tenantSlug}/{path1}/{path2}")
    public String forwardThreeSegmentRoutes() {
        return "forward:/index.html";
    }

    // ✅ Optional: root and known static routes
    @GetMapping({"/", "/login", "/register", "/forgot-password", "/reset-password"})
    public String forwardKnownRoutes() {
        return "forward:/index.html";
    }
}
