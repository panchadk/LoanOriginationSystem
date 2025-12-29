/*
package com.adminplatform.los_auth.authorize.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins(
                                "http://localhost:3000",
                                "https://4da544318f60.ngrok-free.app"
                        )
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*");
            }
        };
    }

}
*/
