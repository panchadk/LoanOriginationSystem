package com.adminplatform.los_auth.authorize.controller;
import com.adminplatform.los_auth.authorize.context.TenantContext;
import com.adminplatform.los_auth.authorize.dto.JwtResponse;
import com.adminplatform.los_auth.authorize.dto.LoginRequest;
import com.adminplatform.los_auth.authorize.dto.RegisterRequest;
import com.adminplatform.los_auth.authorize.dto.ResetPasswordRequest;
import com.adminplatform.los_auth.authorize.entity.PasswordResetToken;
import com.adminplatform.los_auth.authorize.entity.UserAccount;
import com.adminplatform.los_auth.authorize.entity.UserRoleMap;
import com.adminplatform.los_auth.authorize.repository.UserAccountRepository;
import com.adminplatform.los_auth.authorize.repository.UserRoleMapRepository;
import com.adminplatform.los_auth.authorize.repository.PasswordResetTokenRepository;
import com.adminplatform.los_auth.authorize.security.JwtUtil;
import com.adminplatform.los_auth.authorize.service.AuthService;
import com.adminplatform.los_auth.authorize.exception.InvalidTokenException;
import com.adminplatform.los_auth.authorize.exception.ExpiredTokenException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final UserAccountRepository userAccountRepository;
    private final UserRoleMapRepository userRoleMapRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;

    public AuthController(
            UserAccountRepository userAccountRepository,
            UserRoleMapRepository userRoleMapRepository,
            PasswordResetTokenRepository tokenRepository,
            JwtUtil jwtUtil,
            PasswordEncoder passwordEncoder,
            AuthService authService
    ) {
        this.userAccountRepository = userAccountRepository;
        this.userRoleMapRepository = userRoleMapRepository;
        this.tokenRepository = tokenRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletRequest httpRequest) {
        String normalizedEmail = request.getEmail().toLowerCase();

        String tenantSlug = httpRequest.getHeader("X-Tenant-ID");
        if (tenantSlug == null || tenantSlug.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing tenant header");
        }

        String token;
        try {
            token = authService.authenticateAndGenerateToken(normalizedEmail, request.getPassword());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        logger.info("Register endpoint triggered for username: {}", request.getUsername());

        try {
            UserAccount user = new UserAccount();
            user.setEmail(request.getEmail().toLowerCase());
            user.setUsername(request.getUsername().toLowerCase());
            user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setTenantSlug(request.getTenantSlug());
            user.setTenantId(request.getTenantId());
            user.setCreatedAt(LocalDateTime.now());
            user.setStatus("ACTIVE");

            userAccountRepository.save(user);

            for (UUID roleId : request.getRoles()) {
                UserRoleMap map = new UserRoleMap(user.getUserId(), user.getTenantId(), roleId);
                userRoleMapRepository.save(map);
            }

            logger.info("User {} registered successfully", user.getUsername());
            return ResponseEntity.ok("User registered successfully");

        } catch (DataIntegrityViolationException e) {
            logger.warn("Registration failed — duplicate email or constraint violation: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists for this tenant.");
        } catch (Exception e) {
            logger.error("Registration failed due to server error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed due to server error.");
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            @RequestBody ResetPasswordRequest request,
            HttpServletRequest httpRequest) {

        Optional<PasswordResetToken> tokenOpt = tokenRepository.findByToken(request.getToken());
        if (tokenOpt.isEmpty()) {
            throw new InvalidTokenException("Token not found");
        }

        PasswordResetToken tokenEntity = tokenOpt.get();

        if (tokenEntity.getExpiry().isBefore(LocalDateTime.now())) {
            throw new ExpiredTokenException("Token has expired");
        }

        String requestTenantId = httpRequest.getHeader("X-Tenant-ID");
        if (requestTenantId == null || requestTenantId.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing tenant header");
        }

        if (!tokenEntity.getTenantId().toString().equals(requestTenantId)) {
            throw new InvalidTokenException("Token does not belong to this tenant");
        }

        UserAccount user = tokenEntity.getUser();
        user.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
        userAccountRepository.save(user);

        tokenRepository.delete(tokenEntity);

        return ResponseEntity.ok("Password reset successful");
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        SecurityContextHolder.clearContext();

        Cookie jwtCookie = new Cookie("jwt", null);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0);
        response.addCookie(jwtCookie);

        Map<String, String> result = new HashMap<>();
        result.put("status", "logged_out");
        return ResponseEntity.ok(result);
    }

    @GetMapping("/heartbeat")
    public ResponseEntity<?> heartbeat(HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getName())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("status", "active");
        response.put("user", auth.getName());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("Test successful");
    }
}
