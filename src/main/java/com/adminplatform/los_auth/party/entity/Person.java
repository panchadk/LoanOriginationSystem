package com.adminplatform.los_auth.party.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "person")
@IdClass(PartyId.class)   // ✅ Use composite PK (tenant_id + party_id)
public class Person {

    @Id
    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Id
    @Column(name = "party_id", nullable = false)
    private UUID partyId;

    @Column(name = "given_name")
    private String givenName;

    @Column(name = "family_name")
    private String familyName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "residency_status")
    private String residencyStatus;

    // ✅ Explicit composite join to Party
    @OneToOne
    @JoinColumns({
            @JoinColumn(name = "tenant_id", referencedColumnName = "tenant_id", insertable = false, updatable = false),
            @JoinColumn(name = "party_id", referencedColumnName = "party_id", insertable = false, updatable = false)
    })
    private Party party;

    // --- Getters and Setters ---
    public UUID getTenantId() { return tenantId; }
    public void setTenantId(UUID tenantId) { this.tenantId = tenantId; }

    public UUID getPartyId() { return partyId; }
    public void setPartyId(UUID partyId) { this.partyId = partyId; }

    public String getGivenName() { return givenName; }
    public void setGivenName(String givenName) { this.givenName = givenName; }

    public String getFamilyName() { return familyName; }
    public void setFamilyName(String familyName) { this.familyName = familyName; }

    public String getMiddleName() { return middleName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }

    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }

    public String getResidencyStatus() { return residencyStatus; }
    public void setResidencyStatus(String residencyStatus) { this.residencyStatus = residencyStatus; }

    public Party getParty() { return party; }
    public void setParty(Party party) { this.party = party; }
}
