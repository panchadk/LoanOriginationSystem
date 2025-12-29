package com.adminplatform.los_auth.authorize.controller;

import com.adminplatform.los_auth.authorize.dto.RegisterRequest;
import com.adminplatform.los_auth.authorize.dto.UserDto;
import com.adminplatform.los_auth.authorize.entity.UserAccount;
import com.adminplatform.los_auth.authorize.entity.UserRoleMap;
import com.adminplatform.los_auth.authorize.repository.UserAccountRepository;
import com.adminplatform.los_auth.authorize.repository.UserRoleMapRepository;
import com.adminplatform.los_auth.authorize.service.AuthService;
import com.adminplatform.los_auth.authorize.service.PasswordResetService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")

public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private UserRoleMapRepository userRoleMapRepository;
    @Autowired
    private PasswordResetService passwordResetService; // ✅ Add this here

    private final AuthService authService;

    public AdminController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/users")
    public ResponseEntity<Map<String, Object>> listUsers() {
        try {
            UUID tenantId = authService.getCurrentTenantId();
            List<UserAccount> users = authService.listUsersByTenant(tenantId);

            List<UserDto> userDtos = users.stream().map(user -> {
                List<UUID> roleIds = userRoleMapRepository.findByUserId(user.getUserId())
                        .stream()
                        .map(UserRoleMap::getRoleId)
                        .toList();

                return new UserDto(
                        user.getUserId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getStatus(),
                        user.getTenantId(),
                        user.getTenantSlug(),
                        roleIds
                );
            }).toList();

            return ResponseEntity.ok(Map.of("users", userDtos));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
        }
    }


    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody RegisterRequest request) {
        logger.info("Admin creating user: {}", request.getUsername());

        try {
            // Step 1: Create and save user
            UserAccount user = new UserAccount();
            user.setEmail(request.getEmail().toLowerCase());
            user.setUsername(request.getUsername().toLowerCase());
            user.setPasswordHash(passwordEncoder.encode(request.getPassword())); // Optional: use placeholder if password is temporary
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setTenantSlug(request.getTenantSlug());
            user.setTenantId(request.getTenantId());
            user.setCreatedAt(LocalDateTime.now());
            user.setStatus("ACTIVE");
            userAccountRepository.save(user);

            // Step 2: Assign roles
            for (UUID roleId : request.getRoles()) {
                UserRoleMap map = new UserRoleMap(user.getUserId(), user.getTenantId(), roleId);
                userRoleMapRepository.save(map);
            }

            // Step 3: Send password reset invitation
            passwordResetService.sendResetLink(user.getEmail());

            logger.info("User {} created and reset link sent", user.getUsername());
            return ResponseEntity.ok("User created and invitation email sent.");

        } catch (DataIntegrityViolationException e) {
            logger.warn("Admin user creation failed — duplicate email or constraint violation: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists for this tenant.");
        } catch (Exception e) {
            logger.error("Admin user creation failed due to server error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User creation failed due to server error.");
        }
    }


    @PutMapping("/users/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable UUID userId, @RequestBody RegisterRequest request) {
        logger.info("Admin updating user: {}", request.getUsername());

        try {
            Optional<UserAccount> optionalUser = userAccountRepository.findById(userId);
            if (optionalUser.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            UserAccount user = optionalUser.get();
            user.setEmail(request.getEmail().toLowerCase());
            user.setUsername(request.getUsername().toLowerCase());
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setTenantSlug(request.getTenantSlug());
            user.setTenantId(request.getTenantId());
          //  user.setStatus(request.getStatus() != null ? request.getStatus() : user.getStatus());
            user.setStatus("ACTIVE");
            if (request.getPassword() != null && !request.getPassword().isBlank()) {
                user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
            }

            userAccountRepository.save(user);

            userRoleMapRepository.deleteByUserId(userId);
            for (UUID roleId : request.getRoles()) {
                UserRoleMap map = new UserRoleMap(user.getUserId(), user.getTenantId(), roleId);
                userRoleMapRepository.save(map);
            }

            logger.info("User {} updated successfully", user.getUsername());
            return ResponseEntity.ok("User updated successfully");

        } catch (Exception e) {
            logger.error("User update failed: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User update failed");
        }
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID userId) {
        logger.info("Admin deleting user: {}", userId);

        try {
            // Delete role mappings first
            userRoleMapRepository.deleteByUserId(userId);

            // Then delete the user
            userAccountRepository.deleteById(userId);

            logger.info("User {} deleted successfully", userId);
            return ResponseEntity.ok("User deleted successfully");
        } catch (Exception e) {
            logger.error("User deletion failed: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User deletion failed");
        }
    }


}
