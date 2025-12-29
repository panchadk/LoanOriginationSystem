package com.adminplatform.los_auth.deal.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "deal_property_party")
public class DealPropertyParty {

    @EmbeddedId
    private DealPropertyPartyId id;

    @Column(name = "effective_to")
    private LocalDate effectiveTo;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void onCreate() { createdAt = LocalDateTime.now(); }

    // Getters/setters
    public DealPropertyPartyId getId() { return id; }
    public void setId(DealPropertyPartyId id) { this.id = id; }
    public LocalDate getEffectiveTo() { return effectiveTo; }
    public void setEffectiveTo(LocalDate effectiveTo) { this.effectiveTo = effectiveTo; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}

@Embeddable
class DealPropertyPartyId implements Serializable {
    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Column(name = "deal_property_id", nullable = false)
    private UUID dealPropertyId;

    @Column(name = "party_id", nullable = false)
    private UUID partyId;

    @Column(name = "role_code", nullable = false, length = 30)
    private String roleCode; // TITLE_HOLDER / CONSENTING_SPOUSE / APPRAISER

    @Column(name = "effective_from", nullable = false)
    private LocalDate effectiveFrom;

    public DealPropertyPartyId() {}
    public DealPropertyPartyId(UUID tenantId, UUID dealPropertyId, UUID partyId, String roleCode, LocalDate effectiveFrom) {
        this.tenantId = tenantId; this.dealPropertyId = dealPropertyId; this.partyId = partyId; this.roleCode = roleCode; this.effectiveFrom = effectiveFrom;
    }
    public UUID getTenantId() { return tenantId; }
    public void setTenantId(UUID tenantId) { this.tenantId = tenantId; }
    public UUID getDealPropertyId() { return dealPropertyId; }
    public void setDealPropertyId(UUID dealPropertyId) { this.dealPropertyId = dealPropertyId; }
    public UUID getPartyId() { return partyId; }
    public void setPartyId(UUID partyId) { this.partyId = partyId; }
    public String getRoleCode() { return roleCode; }
    public void setRoleCode(String roleCode) { this.roleCode = roleCode; }
    public LocalDate getEffectiveFrom() { return effectiveFrom; }
    public void setEffectiveFrom(LocalDate effectiveFrom) { this.effectiveFrom = effectiveFrom; }

    @Override public boolean equals(Object o) { if (this == o) return true; if (!(o instanceof DealPropertyPartyId)) return false; DealPropertyPartyId that = (DealPropertyPartyId) o; return tenantId.equals(that.tenantId) && dealPropertyId.equals(that.dealPropertyId) && partyId.equals(that.partyId) && roleCode.equals(that.roleCode) && effectiveFrom.equals(that.effectiveFrom); }
    @Override public int hashCode() { return java.util.Objects.hash(tenantId, dealPropertyId, partyId, roleCode, effectiveFrom); }
}
