package com.adminplatform.los_auth.authorize.controller;

import com.adminplatform.los_auth.authorize.entity.UserAccount;
import com.adminplatform.los_auth.authorize.repository.UserAccountRepository;
import com.adminplatform.los_auth.authorize.service.PasswordResetService;
//import org.apache.el.stream.Optional;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class PasswordController {

    @Autowired
    private PasswordResetService passwordResetService;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        if (email == null || email.isBlank()) {
            return ResponseEntity.badRequest().body("Email is required");
        }

        Optional<UserAccount> userOpt = userAccountRepository.findByEmailIgnoreCase(email.trim());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        UserAccount user = userOpt.get();
        passwordResetService.sendResetLink(email);

        return ResponseEntity.ok("Reset link sent");
    }
}

