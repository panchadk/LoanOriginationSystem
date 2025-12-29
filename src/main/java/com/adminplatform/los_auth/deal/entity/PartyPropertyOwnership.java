package com.adminplatform.los_auth.deal.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "party_property_ownership")
public class PartyPropertyOwnership {

    @EmbeddedId
    private PartyPropertyOwnershipId id;

    @Column(name = "ownership_type", nullable = false, length = 30)
    private String ownershipType;

    @Column(name = "ownership_share_pct", nullable = false, precision = 5, scale = 2)
    private BigDecimal ownershipSharePct;

    @Column(name = "effective_to")
    private LocalDate effectiveTo;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void onCreate() { createdAt = LocalDateTime.now(); }

    // Getters/setters
    public PartyPropertyOwnershipId getId() { return id; }
    public void setId(PartyPropertyOwnershipId id) { this.id = id; }
    public String getOwnershipType() { return ownershipType; }
    public void setOwnershipType(String ownershipType) { this.ownershipType = ownershipType; }
    public BigDecimal getOwnershipSharePct() { return ownershipSharePct; }
    public void setOwnershipSharePct(BigDecimal ownershipSharePct) { this.ownershipSharePct = ownershipSharePct; }
    public LocalDate getEffectiveTo() { return effectiveTo; }
    public void setEffectiveTo(LocalDate effectiveTo) { this.effectiveTo = effectiveTo; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}

@Embeddable
class PartyPropertyOwnershipId implements Serializable {
    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Column(name = "property_id", nullable = false)
    private UUID propertyId;

    @Column(name = "party_id", nullable = false)
    private UUID partyId;

    @Column(name = "effective_from", nullable = false)
    private LocalDate effectiveFrom;

    public PartyPropertyOwnershipId() {}
    public PartyPropertyOwnershipId(UUID tenantId, UUID propertyId, UUID partyId, LocalDate effectiveFrom) {
        this.tenantId = tenantId; this.propertyId = propertyId; this.partyId = partyId; this.effectiveFrom = effectiveFrom;
    }
    public UUID getTenantId() { return tenantId; }
    public void setTenantId(UUID tenantId) { this.tenantId = tenantId; }
    public UUID getPropertyId() { return propertyId; }
    public void setPropertyId(UUID propertyId) { this.propertyId = propertyId; }
    public UUID getPartyId() { return partyId; }
    public void setPartyId(UUID partyId) { this.partyId = partyId; }
    public LocalDate getEffectiveFrom() { return effectiveFrom; }
    public void setEffectiveFrom(LocalDate effectiveFrom) { this.effectiveFrom = effectiveFrom; }

    @Override public boolean equals(Object o) { if (this == o) return true; if (!(o instanceof PartyPropertyOwnershipId)) return false; PartyPropertyOwnershipId that = (PartyPropertyOwnershipId) o; return tenantId.equals(that.tenantId) && propertyId.equals(that.propertyId) && partyId.equals(that.partyId) && effectiveFrom.equals(that.effectiveFrom); }
    @Override public int hashCode() { return java.util.Objects.hash(tenantId, propertyId, partyId, effectiveFrom); }
}
