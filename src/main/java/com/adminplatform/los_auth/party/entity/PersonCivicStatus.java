package com.adminplatform.los_auth.party.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "person_civic_status")
public class PersonCivicStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "civic_status_id")
    private UUID civicStatusId;

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Column(name = "party_id", nullable = false)
    private UUID partyId;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "effective_from", nullable = false)
    private LocalDate effectiveFrom;

    @Column(name = "effective_to")
    private LocalDate effectiveTo;

    public UUID getCivicStatusId() {
        return civicStatusId;
    }

    public void setCivicStatusId(UUID civicStatusId) {
        this.civicStatusId = civicStatusId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(LocalDate effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public LocalDate getEffectiveTo() {
        return effectiveTo;
    }

    public void setEffectiveTo(LocalDate effectiveTo) {
        this.effectiveTo = effectiveTo;
    }

    public PersonCivicStatus() {}

    // Getters and setters omitted for brevity
}
