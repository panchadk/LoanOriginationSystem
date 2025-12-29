package com.adminplatform.los_auth.party.dto;

import java.util.UUID;

public class PartySummaryDto {
    private final UUID partyId;
    private final String kind;
    private final String status;
    private final String createdAt;
    private final String givenName;
    private final String familyName;
    private final String legalName;

    // Constructor
    public PartySummaryDto(UUID partyId, String kind, String status, String createdAt,
                           String givenName, String familyName, String legalName) {
        this.partyId = partyId;
        this.kind = kind;
        this.status = status;
        this.createdAt = createdAt;
        this.givenName = givenName;
        this.familyName = familyName;
        this.legalName = legalName;
    }

    // Getters
    public UUID getPartyId() {
        return partyId;
    }

    public String getKind() {
        return kind;
    }

    public String getStatus() {
        return status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getLegalName() {
        return legalName;
    }
}
