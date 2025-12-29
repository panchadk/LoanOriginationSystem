package com.adminplatform.los_auth.party.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "party")
@IdClass(PartyId.class)   // ✅ Use PartyId as composite PK
public class Party {

    @Id
    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Id
    @Column(name = "party_id", nullable = false)
    private UUID partyId;

    @Column(name = "kind", nullable = false)
    private String kind;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at") // Nullable since it's only set during updates
    private Instant updatedAt;

    // Relationships
    @OneToOne(mappedBy = "party", cascade = CascadeType.ALL, orphanRemoval = true)
    private Person person;

    @OneToOne(mappedBy = "party", cascade = CascadeType.ALL, orphanRemoval = true)
    private Organization organization;

    // --- Getters and Setters ---
    public UUID getTenantId() { return tenantId; }
    public void setTenantId(UUID tenantId) { this.tenantId = tenantId; }

    public UUID getPartyId() { return partyId; }
    public void setPartyId(UUID partyId) { this.partyId = partyId; }

    public String getKind() { return kind; }
    public void setKind(String kind) { this.kind = kind; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }

    public Person getPerson() { return person; }
    public void setPerson(Person person) { this.person = person; }

    public Organization getOrganization() { return organization; }
    public void setOrganization(Organization organization) { this.organization = organization; }
}
