package com.adminplatform.los_auth.deal.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class DealPartyId implements Serializable {

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Column(name = "deal_id", nullable = false)
    private UUID dealId;

    @Column(name = "party_id", nullable = false)
    private UUID partyId;

    @Column(name = "role_code", nullable = false, length = 30)
    private String roleCode; // BORROWER / CO_BORROWER / GUARANTOR / BROKER / ATTORNEY / INVESTOR

    @Column(name = "effective_from", nullable = false)
    private LocalDate effectiveFrom;

    public DealPartyId() {}

    public DealPartyId(UUID tenantId, UUID dealId, UUID partyId, String roleCode, LocalDate effectiveFrom) {
        this.tenantId = tenantId;
        this.dealId = dealId;
        this.partyId = partyId;
        this.roleCode = roleCode;
        this.effectiveFrom = effectiveFrom;
    }

    // Getters & Setters
    public UUID getTenantId() { return tenantId; }
    public void setTenantId(UUID tenantId) { this.tenantId = tenantId; }

    public UUID getDealId() { return dealId; }
    public void setDealId(UUID dealId) { this.dealId = dealId; }

    public UUID getPartyId() { return partyId; }
    public void setPartyId(UUID partyId) { this.partyId = partyId; }

    public String getRoleCode() { return roleCode; }
    public void setRoleCode(String roleCode) { this.roleCode = roleCode; }

    public LocalDate getEffectiveFrom() { return effectiveFrom; }
    public void setEffectiveFrom(LocalDate effectiveFrom) { this.effectiveFrom = effectiveFrom; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DealPartyId)) return false;
        DealPartyId that = (DealPartyId) o;
        return Objects.equals(tenantId, that.tenantId) &&
                Objects.equals(dealId, that.dealId) &&
                Objects.equals(partyId, that.partyId) &&
                Objects.equals(roleCode, that.roleCode) &&
                Objects.equals(effectiveFrom, that.effectiveFrom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tenantId, dealId, partyId, roleCode, effectiveFrom);
    }
}
