package com.adminplatform.los_auth.deal.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "deal_party")
public class DealParty {

    @EmbeddedId
    private DealPartyId id;

    @Column(name = "liability_pct", precision = 5, scale = 2)
    private BigDecimal liabilityPct;

    @Column(name = "guarantee_type", length = 50)
    private String guaranteeType;

    @Column(name = "guarantee_cap_amount", precision = 18, scale = 2)
    private BigDecimal guaranteeCapAmount;

    @Column(name = "effective_to")
    private LocalDate effectiveTo;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // Getters & Setters
    public DealPartyId getId() { return id; }
    public void setId(DealPartyId id) { this.id = id; }

    public BigDecimal getLiabilityPct() { return liabilityPct; }
    public void setLiabilityPct(BigDecimal liabilityPct) { this.liabilityPct = liabilityPct; }

    public String getGuaranteeType() { return guaranteeType; }
    public void setGuaranteeType(String guaranteeType) { this.guaranteeType = guaranteeType; }

    public BigDecimal getGuaranteeCapAmount() { return guaranteeCapAmount; }
    public void setGuaranteeCapAmount(BigDecimal guaranteeCapAmount) { this.guaranteeCapAmount = guaranteeCapAmount; }

    public LocalDate getEffectiveTo() { return effectiveTo; }
    public void setEffectiveTo(LocalDate effectiveTo) { this.effectiveTo = effectiveTo; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }


    public void setTenantId(UUID tenantId) {
        if (id == null) id = new DealPartyId();
        id.setTenantId(tenantId);
    }

    public void setDealId(UUID dealId) {
        if (id == null) id = new DealPartyId();
        id.setDealId(dealId);
    }

    public void setPartyId(UUID partyId) {
        if (id == null) id = new DealPartyId();
        id.setPartyId(partyId);
    }

    public void setRoleCode(String roleCode) {
        if (id == null) id = new DealPartyId();
        id.setRoleCode(roleCode);
    }

    public void setEffectiveFrom(LocalDate effectiveFrom) {
        if (id == null) id = new DealPartyId();
        id.setEffectiveFrom(effectiveFrom);
    }

}
