package com.adminplatform.los_auth.authorize.service;

import com.adminplatform.los_auth.authorize.dto.RegisterRequest;
import com.adminplatform.los_auth.authorize.entity.UserAccount;
import com.adminplatform.los_auth.authorize.repository.UserAccountRepository;
import com.adminplatform.los_auth.authorize.repository.UserRoleMapRepository;
import com.adminplatform.los_auth.authorize.security.CustomUserDetails;
import com.adminplatform.los_auth.authorize.security.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private UserRoleMapRepository userRoleMapRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public void registerUser(RegisterRequest request) {
        Optional<UserAccount> existing = userAccountRepository.findByEmailIgnoreCase(request.getEmail());
        if (existing.isPresent()) {
            throw new RuntimeException("User already exists");
        }

        UserAccount user = new UserAccount();
        user.setEmail(request.getEmail());
        user.setPasswordHash(hashPassword(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setTenantSlug(request.getTenantSlug());
        try {
            UUID tenantId = request.getTenantId();
            user.setTenantId(tenantId);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid tenant ID format");
        }

        user.setStatus("ACTIVE");
        user.setCreatedAt(LocalDateTime.now());

        userAccountRepository.save(user);
    }

    public String authenticateAndGenerateToken(String email, String password) {
        System.out.println("🔐 Attempting login for: " + email);

        Optional<UserAccount> optionalUser = userAccountRepository.findByEmailIgnoreCase(email);
        if (optionalUser.isEmpty()) {
            System.out.println("❌ User not found: " + email);
            throw new RuntimeException("User not found");
        }

        UserAccount user = optionalUser.get();

        System.out.println("🔐 Found user: " + user.getEmail());
        System.out.println("🔐 Checking password...");

        if (!BCrypt.checkpw(password, user.getPasswordHash())) {
            System.out.println("❌ Invalid password for: " + email);
            throw new RuntimeException("Invalid credentials");
        }

        System.out.println("✅ Password verified for: " + email);
        System.out.println("✅ Calling native query: fetchRoleNamesForJwt");

        List<String> roles = userRoleMapRepository.fetchRoleNamesForJwt(user.getUserId());
        System.out.println("🛠️ Roles fetched for user " + user.getEmail() + ": " + roles);

        System.out.println("🔐 Preparing to generate token...");
        System.out.println("🔐 Email: " + user.getEmail());
        System.out.println("🔐 User ID: " + user.getUserId());
        System.out.println("🔐 Tenant ID: " + user.getTenantId());
        System.out.println("🔐 Roles: " + roles);

        String token = jwtUtil.generateToken(user, roles);
        System.out.println("✅ Token generated successfully");

        return token;
    }



    public List<UserAccount> listUsersByTenant(UUID tenantId) {
        return userAccountRepository.findByTenantId(tenantId);
    }

    private String hashPassword(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    public UUID getCurrentTenantId() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User is not authenticated");
        }

        Object principal = authentication.getPrincipal();
        System.out.println("Principal type: " + principal.getClass().getName());

        try {
            if (principal instanceof String) {
                // Case 1: Principal is a String (JWT-based authentication)
                System.out.println("Principal is a String: " + principal);

                // Extract the tenant ID from the JWT token
                String token = extractTokenFromRequest();
                return extractTenantIdFromToken(token);
            } else if (principal instanceof UserDetails) {
                // Case 2: Principal is a UserDetails object
                System.out.println("Principal is UserDetails");

                // Extract the tenant ID from the UserDetails attributes
                UserDetails userDetails = (UserDetails) principal;
                Map<String, Object> attributes = (Map<String, Object>) ((CustomUserDetails) userDetails).getAttributes();
                String tenantId = (String) attributes.get("tenantId");
                if (tenantId == null) {
                    throw new RuntimeException("Tenant ID not found in user details");
                }
                return UUID.fromString(tenantId);
            } else {
                throw new RuntimeException("Unexpected principal type: " + principal.getClass().getName());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve tenant ID: " + e.getMessage(), e);
        }
    }
    private UUID extractTenantIdFromToken(String token) {
        try {
            Claims claims = jwtUtil.extractAllClaims(token);
            String tenantId = claims.get("tenant_id", String.class);
            if (tenantId == null) {
                throw new RuntimeException("Tenant ID not found in token");
            }
            return UUID.fromString(tenantId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract tenant ID from token: " + e.getMessage());
        }
    }

    private String extractTokenFromRequest() {
        // Implement logic to extract the JWT token from the request header
        // For example:
        var request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid or missing token");
        }
        return authorizationHeader.substring(7); // Remove "Bearer " prefix
    }

    public List<UserAccount> listUsersByCurrentTenant() {
        // Get the tenant ID of the currently logged-in user
        UUID tenantId = getCurrentTenantId();

        // Fetch users for the tenant
        return userAccountRepository.findByTenantId(tenantId);
    }
}


