package com.adminplatform.los_auth.authorize.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class TenantDto {
    private UUID tenantId;
    private String name;
    private String slug;
    private String status;
    private String createdBy;
    private LocalDateTime createdAt;

    // ✅ No-args constructor
    public TenantDto() {}

    // ✅ All-args constructor
    public TenantDto(UUID tenantId, String name, String slug, String status, String createdBy, LocalDateTime createdAt) {
        this.tenantId = tenantId;
        this.name = name;
        this.slug = slug;
        this.status = status;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

    // ✅ Getters
    public UUID getTenantId() {
        return tenantId;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public String getStatus() {
        return status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // ✅ Setters
    public void setTenantId(UUID tenantId) {
        this.tenantId = tenantId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
