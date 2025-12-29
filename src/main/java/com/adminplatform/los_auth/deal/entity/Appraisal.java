package com.adminplatform.los_auth.deal.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "appraisal")
public class Appraisal {

    @EmbeddedId
    private AppraisalId id;

    @Column(name = "property_id", nullable = false)
    private UUID propertyId;

    @Column(name = "deal_property_id")
    private UUID dealPropertyId;

    @Column(name = "appraiser_party_id", nullable = false)
    private UUID appraiserPartyId;

    @Column(name = "value", nullable = false, precision = 18, scale = 2)
    private BigDecimal value;

    @Column(name = "as_of_date", nullable = false)
    private LocalDate asOfDate;

    @Column(name = "accepted", nullable = false)
    private Boolean accepted = false;

    @Column(name = "expires_at")
    private LocalDate expiresAt;

    @Column(name = "document_id")
    private UUID documentId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    void onCreate() { createdAt = LocalDateTime.now(); updatedAt = createdAt; }
    @PreUpdate
    void onUpdate() { updatedAt = LocalDateTime.now(); }

    // Getters/setters
    public AppraisalId getId() { return id; }
    public void setId(AppraisalId id) { this.id = id; }
    public UUID getPropertyId() { return propertyId; }
    public void setPropertyId(UUID propertyId) { this.propertyId = propertyId; }
    public UUID getDealPropertyId() { return dealPropertyId; }
    public void setDealPropertyId(UUID dealPropertyId) { this.dealPropertyId = dealPropertyId; }
    public UUID getAppraiserPartyId() { return appraiserPartyId; }
    public void setAppraiserPartyId(UUID appraiserPartyId) { this.appraiserPartyId = appraiserPartyId; }
    public BigDecimal getValue() { return value; }
    public void setValue(BigDecimal value) { this.value = value; }
    public LocalDate getAsOfDate() { return asOfDate; }
    public void setAsOfDate(LocalDate asOfDate) { this.asOfDate = asOfDate; }
    public Boolean getAccepted() { return accepted; }
    public void setAccepted(Boolean accepted) { this.accepted = accepted; }
    public LocalDate getExpiresAt() { return expiresAt; }
    public void setExpiresAt(LocalDate expiresAt) { this.expiresAt = expiresAt; }
    public UUID getDocumentId() { return documentId; }
    public void setDocumentId(UUID documentId) { this.documentId = documentId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}

@Embeddable
class AppraisalId implements Serializable {
    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Column(name = "appraisal_id", nullable = false)
    private UUID appraisalId;

    public AppraisalId() {}
    public AppraisalId(UUID tenantId, UUID appraisalId) { this.tenantId = tenantId; this.appraisalId = appraisalId; }
    public UUID getTenantId() { return tenantId; }
    public void setTenantId(UUID tenantId) { this.tenantId = tenantId; }
    public UUID getAppraisalId() { return appraisalId; }
    public void setAppraisalId(UUID appraisalId) { this.appraisalId = appraisalId; }

    @Override public boolean equals(Object o) { if (this == o) return true; if (!(o instanceof AppraisalId)) return false; AppraisalId that = (AppraisalId) o; return tenantId.equals(that.tenantId) && appraisalId.equals(that.appraisalId); }
    @Override public int hashCode() { return java.util.Objects.hash(tenantId, appraisalId); }
}
