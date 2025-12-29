package com.adminplatform.los_auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {
        "com.adminplatform.los_auth.authorize.entity",
        "com.adminplatform.los_auth.party.entity",
        "com.adminplatform.los_auth.outbox.entity",
        "com.adminplatform.los_auth.audit.entity",
        "com.adminplatform.los_auth.deal.entity"   // ✅ added deal entities
})
@EnableJpaRepositories(basePackages = {
        "com.adminplatform.los_auth.authorize.repository",
        "com.adminplatform.los_auth.party.repository",
        "com.adminplatform.los_auth.outbox.repository",
        "com.adminplatform.los_auth.audit.repository",
        "com.adminplatform.los_auth.deal.repository"   // ✅ added deal repositories
})
@ComponentScan(basePackages = {
        "com.adminplatform.los_auth"   // ✅ scans everything under los_auth, including deal
})
public class LosAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(LosAuthApplication.class, args);
    }
}
