package com.adminplatform.los_auth.authorize.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_account")
public class UserAccount {

    @Id
    @GeneratedValue
    @Column(name = "user_id", updatable = false, nullable = false)
    private UUID userId;

    @Column(name = "username")
    private String username;

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "status", nullable = false)
    private String status; // ACTIVE, INACTIVE, INVITED

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by")
    private UUID updatedBy;
    @Column(name = "tenant_slug")
    private String tenantSlug;


    // ✅ Getters

    public String getTenantSlug() {
        return tenantSlug;
    }

    public UUID getUserId() { return userId; }
    public String getUsername(){return username;}
    public UUID getTenantId() { return tenantId; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public UUID getCreatedBy() { return createdBy; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public UUID getUpdatedBy() { return updatedBy; }

    // ✅ Setters
    public void setTenantSlug(String tenantSlug) {
        this.tenantSlug = tenantSlug;
    }
    public void setUserId(UUID userId) { this.userId = userId; }
    public void setUsername(String username) {this.username=username;}
    public void setTenantId(UUID tenantId) { this.tenantId = tenantId; }
    public void setEmail(String email) { this.email = email; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setStatus(String status) { this.status = status; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setCreatedBy(UUID createdBy) { this.createdBy = createdBy; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public void setUpdatedBy(UUID updatedBy) { this.updatedBy = updatedBy; }
}
