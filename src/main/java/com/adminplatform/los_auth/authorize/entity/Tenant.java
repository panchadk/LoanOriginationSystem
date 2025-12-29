package com.adminplatform.los_auth.authorize.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tenant")
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tenant_id")
    private UUID tenantId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(nullable = false, unique = true)
    private String slug;

    // ✅ Getters and Setters

    public UUID getTenantId() {
        return tenantId;
    }

    public void setTenantId(UUID tenantId) {
        this.tenantId = tenantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }




    public void setCreateAt(LocalDateTime createAt) {
        this.createdAt = createAt;
    }


    public void setCreatedAt(LocalDateTime createAt) {
        this.createdAt = createAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    // Optional alias for clarity
    public String getTenantSlug() {
        return slug;
    }
}
