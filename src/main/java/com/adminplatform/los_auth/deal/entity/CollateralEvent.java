package com.adminplatform.los_auth.deal.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "collateral_event")
public class CollateralEvent {

    @EmbeddedId
    private CollateralEventId id;

    @Column(name = "deal_property_id", nullable = false)
    private UUID dealPropertyId;

    @Column(name = "type", nullable = false, length = 30)
    private String type; // ATTACH/RELEASE/SUBSTITUTION/REVALUATION/CURTAILMENT_LINK

    @Column(name = "event_date", nullable = false)
    private LocalDate eventDate;

    @Column(name = "proceeds_amount", precision = 18, scale = 2)
    private BigDecimal proceedsAmount;

    @Column(name = "principal_reduction_amount", precision = 18, scale = 2)
    private BigDecimal principalReductionAmount;

    @Column(name = "fees_amount", precision = 18, scale = 2)
    private BigDecimal feesAmount;

    @Column(name = "document_id")
    private UUID documentId;

    @Column(name = "approved_by")
    private UUID approvedBy;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void onCreate() { createdAt = LocalDateTime.now(); }

    // Getters/setters
    public CollateralEventId getId() { return id; }
    public void setId(CollateralEventId id) { this.id = id; }
    public UUID getDealPropertyId() { return dealPropertyId; }
    public void setDealPropertyId(UUID dealPropertyId) { this.dealPropertyId = dealPropertyId; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public LocalDate getEventDate() { return eventDate; }
    public void setEventDate(LocalDate eventDate) { this.eventDate = eventDate; }
    public BigDecimal getProceedsAmount() { return proceedsAmount; }
    public void setProceedsAmount(BigDecimal proceedsAmount) { this.proceedsAmount = proceedsAmount; }
    public BigDecimal getPrincipalReductionAmount() { return principalReductionAmount; }
    public void setPrincipalReductionAmount(BigDecimal principalReductionAmount) { this.principalReductionAmount = principalReductionAmount; }
    public BigDecimal getFeesAmount() { return feesAmount; }
    public void setFeesAmount(BigDecimal feesAmount) { this.feesAmount = feesAmount; }
    public UUID getDocumentId() { return documentId; }
    public void setDocumentId(UUID documentId) { this.documentId = documentId; }
    public UUID getApprovedBy() { return approvedBy; }
    public void setApprovedBy(UUID approvedBy) { this.approvedBy = approvedBy; }
    public LocalDateTime getApprovedAt() { return approvedAt; }
    public void setApprovedAt(LocalDateTime approvedAt) { this.approvedAt = approvedAt; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}

@Embeddable
class CollateralEventId implements Serializable {
    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Column(name = "event_id", nullable = false)
    private UUID eventId;

    public CollateralEventId() {}
    public CollateralEventId(UUID tenantId, UUID eventId) { this.tenantId = tenantId; this.eventId = eventId; }
    public UUID getTenantId() { return tenantId; }
    public void setTenantId(UUID tenantId) { this.tenantId = tenantId; }
    public UUID getEventId() { return eventId; }
    public void setEventId(UUID eventId) { this.eventId = eventId; }

    @Override public boolean equals(Object o) { if (this == o) return true; if (!(o instanceof CollateralEventId)) return false; CollateralEventId that = (CollateralEventId) o; return tenantId.equals(that.tenantId) && eventId.equals(that.eventId); }
    @Override public int hashCode() { return java.util.Objects.hash(tenantId, eventId); }
}
