package com.adminplatform.los_auth.deal.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "deal_tag")
@IdClass(DealTagId.class)
public class DealTag {

    @Id
    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Id
    @Column(name = "deal_id", nullable = false)
    private UUID dealId;

    @Id
    @Column(name = "tag", nullable = false)
    private String tag;

    @Column(name = "created_by", nullable = false)
    private UUID createdBy;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // Getters and setters
    public UUID getTenantId() { return tenantId; }
    public void setTenantId(UUID tenantId) { this.tenantId = tenantId; }

    public UUID getDealId() { return dealId; }
    public void setDealId(UUID dealId) { this.dealId = dealId; }

    public String getTag() { return tag; }
    public void setTag(String tag) { this.tag = tag; }

    public UUID getCreatedBy() { return createdBy; }
    public void setCreatedBy(UUID createdBy) { this.createdBy = createdBy; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
