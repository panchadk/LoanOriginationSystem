package com.adminplatform.los_auth.party.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.UUID;
import java.time.LocalDate;
import java.util.Objects;

@Embeddable
public class PartyAddressId implements Serializable {

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Column(name = "party_id", nullable = false)
    private UUID partyId;

    @Column(name = "address_id", nullable = false)
    private UUID addressId;

    @Column(name = "effective_from", nullable = false)
    private LocalDate effectiveFrom;

    public PartyAddressId() {}

    public PartyAddressId(UUID tenantId, UUID partyId, UUID addressId, LocalDate effectiveFrom) {
        this.tenantId = tenantId;
        this.partyId = partyId;
        this.addressId = addressId;
        this.effectiveFrom = effectiveFrom;
    }

    // Getters and setters

    public UUID getTenantId() { return tenantId; }
    public void setTenantId(UUID tenantId) { this.tenantId = tenantId; }

    public UUID getPartyId() { return partyId; }
    public void setPartyId(UUID partyId) { this.partyId = partyId; }

    public UUID getAddressId() { return addressId; }
    public void setAddressId(UUID addressId) { this.addressId = addressId; }

    public LocalDate getEffectiveFrom() { return effectiveFrom; }
    public void setEffectiveFrom(LocalDate effectiveFrom) { this.effectiveFrom = effectiveFrom; }

    // equals and hashCode are REQUIRED for composite keys

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PartyAddressId)) return false;
        PartyAddressId that = (PartyAddressId) o;
        return Objects.equals(tenantId, that.tenantId) &&
                Objects.equals(partyId, that.partyId) &&
                Objects.equals(addressId, that.addressId) &&
                Objects.equals(effectiveFrom, that.effectiveFrom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tenantId, partyId, addressId, effectiveFrom);
    }
}
