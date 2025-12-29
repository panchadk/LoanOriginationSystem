package com.adminplatform.los_auth.authorize.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "password_reset_token", uniqueConstraints = {
        @UniqueConstraint(columnNames = "user_id")
})
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserAccount user;

    @Column(nullable = false)
    private LocalDateTime expiry;

    public PasswordResetToken() {}

    public PasswordResetToken(String token, UserAccount user) {
        this.token = token;
        this.user = user;
        this.expiry = LocalDateTime.now().plusMinutes(30);
    }

    // ✅ Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UUID getTenantId() {
        return tenantId;
    }

    public void setTenantId(UUID tenantId) {
        this.tenantId = tenantId;
    }

    public UserAccount getUser() {
        return user;
    }

    public void setUser(UserAccount user) {
        this.user = user;
    }

    public LocalDateTime getExpiry() {
        return expiry;
    }

    public void setExpiry(LocalDateTime expiry) {
        this.expiry = expiry;
    }
}
