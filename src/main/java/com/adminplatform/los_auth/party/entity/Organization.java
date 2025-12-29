package com.adminplatform.los_auth.party.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "organization")
@IdClass(PartyId.class)   // ✅ Composite PK (tenant_id + party_id)
public class Organization {

    @Id
    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Id
    @Column(name = "party_id", nullable = false)
    private UUID partyId;

    @Column(name = "legal_name")
    private String legalName;

    @Column(name = "registration_jurisdiction")
    private String registrationJurisdiction;

    @Column(name = "business_type")
    private String businessType;

    @Column(name = "bin")
    private String bin;

    @Column(name = "created_at")
    private Instant createdAt;

    // ✅ Explicit composite join to Party
    @OneToOne
    @JoinColumns({
            @JoinColumn(name = "tenant_id", referencedColumnName = "tenant_id", insertable = false, updatable = false),
            @JoinColumn(name = "party_id", referencedColumnName = "party_id", insertable = false, updatable = false)
    })
    private Party party;

    // --- Getters and setters ---
    public UUID getTenantId() { return tenantId; }
    public void setTenantId(UUID tenantId) { this.tenantId = tenantId; }

    public UUID getPartyId() { return partyId; }
    public void setPartyId(UUID partyId) { this.partyId = partyId; }

    public String getLegalName() { return legalName; }
    public void setLegalName(String legalName) { this.legalName = legalName; }

    public String getRegistrationJurisdiction() { return registrationJurisdiction; }
    public void setRegistrationJurisdiction(String registrationJurisdiction) { this.registrationJurisdiction = registrationJurisdiction; }

    public String getBusinessType() { return businessType; }
    public void setBusinessType(String businessType) { this.businessType = businessType; }

    public String getBin() { return bin; }
    public void setBin(String bin) { this.bin = bin; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Party getParty() { return party; }
    public void setParty(Party party) { this.party = party; }
}
