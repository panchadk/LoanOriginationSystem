package com.adminplatform.los_auth.authorize.service;

import com.adminplatform.los_auth.authorize.entity.PasswordResetToken;
import com.adminplatform.los_auth.authorize.entity.UserAccount;
import com.adminplatform.los_auth.authorize.repository.PasswordResetTokenRepository;
import com.adminplatform.los_auth.authorize.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordResetService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private EmailService emailService;

    public void sendResetLink(String email) {
        // Step 1: Normalize and validate email
        String normalizedEmail = email.trim().toLowerCase();
        Optional<UserAccount> userOpt = userAccountRepository.findByEmailIgnoreCase(normalizedEmail);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        // Step 2: Extract user and tenant
        UserAccount user = userOpt.get();
        UUID tenantId = user.getTenantId(); // ✅ Ensure tenant ID is captured
        String tenantSlug = user.getTenantSlug(); // ✅ Add this if you store slugs

        // Step 3: Create token
        String token = UUID.randomUUID().toString();
        LocalDateTime expiry = LocalDateTime.now().plusMinutes(30);

        PasswordResetToken resetToken = new PasswordResetToken(token, user);
        resetToken.setTenantId(tenantId);     // ✅ Tag token with tenant ID
        resetToken.setExpiry(expiry);         // ✅ Set expiry

        tokenRepository.save(resetToken);

        // Step 4: Build tenant-aware reset link
        String resetLink = "http://localhost:3000/" + tenantSlug + "/reset-password?token=" + token;

        // Step 5: Send email
        emailService.send(user.getEmail(), "Reset your password", "Click here: " + resetLink);
    }
}
