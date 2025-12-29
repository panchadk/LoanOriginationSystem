package com.adminplatform.los_auth.party.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * Composite primary key for Party entity: (tenant_id, party_id).
 */
@Embeddable
public class PartyId implements Serializable {

    private UUID tenantId;
    private UUID partyId;

    // --- Constructors ---
    public PartyId() {}

    public PartyId(UUID tenantId, UUID partyId) {
        this.tenantId = tenantId;
        this.partyId = partyId;
    }

    // --- Getters & Setters ---
    public UUID getTenantId() { return tenantId; }
    public void setTenantId(UUID tenantId) { this.tenantId = tenantId; }

    public UUID getPartyId() { return partyId; }
    public void setPartyId(UUID partyId) { this.partyId = partyId; }

    // --- equals & hashCode (required for composite keys) ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PartyId)) return false;
        PartyId that = (PartyId) o;
        return Objects.equals(tenantId, that.tenantId) &&
                Objects.equals(partyId, that.partyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tenantId, partyId);
    }
}
