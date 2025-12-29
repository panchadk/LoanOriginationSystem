package com.adminplatform.los_auth.party.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class PartyContactId implements Serializable {

    @Column(name = "tenant_id")
    private UUID tenantId;

    @Column(name = "party_id")
    private UUID partyId;

    @Column(name = "contact_id")
    private UUID contactId;

    @Column(name = "effective_from")
    private LocalDate effectiveFrom;

    public PartyContactId() {}

    // ✅ Add this constructor
    public PartyContactId(UUID tenantId, UUID partyId, UUID contactId, LocalDate effectiveFrom) {
        this.tenantId = tenantId;
        this.partyId = partyId;
        this.contactId = contactId;
        this.effectiveFrom = effectiveFrom;
    }

    // equals and hashCode required

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PartyContactId)) return false;
        PartyContactId that = (PartyContactId) o;
        return Objects.equals(tenantId, that.tenantId) &&
                Objects.equals(partyId, that.partyId) &&
                Objects.equals(contactId, that.contactId) &&
                Objects.equals(effectiveFrom, that.effectiveFrom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tenantId, partyId, contactId, effectiveFrom);
    }

    public UUID getTenantId() {
        return tenantId;
    }

    public void setTenantId(UUID tenantId) {
        this.tenantId = tenantId;
    }

    public UUID getPartyId() {
        return partyId;
    }

    public void setPartyId(UUID partyId) {
        this.partyId = partyId;
    }

    public UUID getContactId() {
        return contactId;
    }

    public void setContactId(UUID contactId) {
        this.contactId = contactId;
    }

    public LocalDate getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(LocalDate effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }
// Getters and setters
}
