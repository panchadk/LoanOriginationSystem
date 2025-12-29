package com.adminplatform.los_auth.deal.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "deal_property")
public class DealProperty {

    @EmbeddedId
    private DealPropertyId id;

    @Column(name = "deal_id", nullable = false)
    private UUID dealId;

    @Column(name = "property_id", nullable = false)
    private UUID propertyId;

    @Column(name = "lien_position", nullable = false)
    private Integer lienPosition;

    @Column(name = "collateral_pct", nullable = false, precision = 5, scale = 2)
    private BigDecimal collateralPct;

    @Column(name = "status", nullable = false, length = 20)
    private String status; // ACTIVE/RELEASED/DISCHARGED

    @Column(name = "release_reason_code")
    private String releaseReasonCode;

    @Column(name = "effective_from", nullable = false)
    private LocalDate effectiveFrom;

    @Column(name = "effective_to")
    private LocalDate effectiveTo;

    @Column(name = "is_primary_residence", nullable = false)
    private Boolean isPrimaryResidence = false;

    @Column(name = "matrimonial_home", nullable = false)
    private Boolean matrimonialHome = false;

    @Column(name = "accepted_value", precision = 18, scale = 2)
    private BigDecimal acceptedValue;

    @Column(name = "accepted_value_asof")
    private LocalDate acceptedValueAsOf;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    void onCreate() { createdAt = LocalDateTime.now(); updatedAt = createdAt; }
    @PreUpdate
    void onUpdate() { updatedAt = LocalDateTime.now(); }

    // Getters/setters
    public DealPropertyId getId() { return id; }
    public void setId(DealPropertyId id) { this.id = id; }
    public UUID getDealId() { return dealId; }
    public void setDealId(UUID dealId) { this.dealId = dealId; }
    public UUID getPropertyId() { return propertyId; }
    public void setPropertyId(UUID propertyId) { this.propertyId = propertyId; }
    public Integer getLienPosition() { return lienPosition; }
    public void setLienPosition(Integer lienPosition) { this.lienPosition = lienPosition; }
    public BigDecimal getCollateralPct() { return collateralPct; }
    public void setCollateralPct(BigDecimal collateralPct) { this.collateralPct = collateralPct; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getReleaseReasonCode() { return releaseReasonCode; }
    public void setReleaseReasonCode(String releaseReasonCode) { this.releaseReasonCode = releaseReasonCode; }
    public LocalDate getEffectiveFrom() { return effectiveFrom; }
    public void setEffectiveFrom(LocalDate effectiveFrom) { this.effectiveFrom = effectiveFrom; }
    public LocalDate getEffectiveTo() { return effectiveTo; }
    public void setEffectiveTo(LocalDate effectiveTo) { this.effectiveTo = effectiveTo; }
    public Boolean getPrimaryResidence() { return isPrimaryResidence; }
    public void setPrimaryResidence(Boolean primaryResidence) { isPrimaryResidence = primaryResidence; }
    public Boolean getMatrimonialHome() { return matrimonialHome; }
    public void setMatrimonialHome(Boolean matrimonialHome) { this.matrimonialHome = matrimonialHome; }
    public BigDecimal getAcceptedValue() { return acceptedValue; }
    public void setAcceptedValue(BigDecimal acceptedValue) { this.acceptedValue = acceptedValue; }
    public LocalDate getAcceptedValueAsOf() { return acceptedValueAsOf; }
    public void setAcceptedValueAsOf(LocalDate acceptedValueAsOf) { this.acceptedValueAsOf = acceptedValueAsOf; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}

@Embeddable
class DealPropertyId implements Serializable {
    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Column(name = "deal_property_id", nullable = false)
    private UUID dealPropertyId;

    public DealPropertyId() {}
    public DealPropertyId(UUID tenantId, UUID dealPropertyId) {
        this.tenantId = tenantId; this.dealPropertyId = dealPropertyId;
    }
    public UUID getTenantId() { return tenantId; }
    public void setTenantId(UUID tenantId) { this.tenantId = tenantId; }
    public UUID getDealPropertyId() { return dealPropertyId; }
    public void setDealPropertyId(UUID dealPropertyId) { this.dealPropertyId = dealPropertyId; }

    @Override public boolean equals(Object o) { if (this == o) return true; if (!(o instanceof DealPropertyId)) return false; DealPropertyId that = (DealPropertyId) o; return tenantId.equals(that.tenantId) && dealPropertyId.equals(that.dealPropertyId); }
    @Override public int hashCode() { return java.util.Objects.hash(tenantId, dealPropertyId); }
}
