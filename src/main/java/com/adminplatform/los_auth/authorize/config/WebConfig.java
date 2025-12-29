package com.adminplatform.los_auth.authorize.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/forgot-password").setViewName("forward:/index.html");
        registry.addViewController("/reset-password").setViewName("forward:/index.html");
        registry.addViewController("/login").setViewName("forward:/index.html");
        registry.addViewController("/register").setViewName("forward:/index.html");
        registry.addViewController("/").setViewName("forward:/index.html");
    }
}
