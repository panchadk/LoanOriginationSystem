package com.adminplatform.los_auth.party.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "party_relationship")
public class PartyRelationship {

    @Id
    @Column(name = "relationship_id")
    private UUID relationshipId;

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    public UUID getRelationshipId() {
        return relationshipId;
    }

    public void setRelationshipId(UUID relationshipId) {
        this.relationshipId = relationshipId;
    }

    public UUID getTenantId() {
        return tenantId;
    }

    public void setTenantId(UUID tenantId) {
        this.tenantId = tenantId;
    }

    public UUID getSrcPartyId() {
        return srcPartyId;
    }

    public void setSrcPartyId(UUID srcPartyId) {
        this.srcPartyId = srcPartyId;
    }

    public UUID getDstPartyId() {
        return dstPartyId;
    }

    public void setDstPartyId(UUID dstPartyId) {
        this.dstPartyId = dstPartyId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    @Column(name = "src_party_id", nullable = false)
    private UUID srcPartyId;

    @Column(name = "dst_party_id", nullable = false)
    private UUID dstPartyId;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "effective_from", nullable = false)
    private LocalDate effectiveFrom;

    @Column(name = "effective_to")
    private LocalDate effectiveTo;

    public PartyRelationship() {}

    // Getters and setters omitted for brevity
}
