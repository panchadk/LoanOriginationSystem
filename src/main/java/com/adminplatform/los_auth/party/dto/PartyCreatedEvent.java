package com.adminplatform.los_auth.party.dto;

import java.util.UUID;

public class PartyCreatedEvent {
    private UUID partyId;
    private UUID tenantId;
    private String kind;
    private String status;
    private String displayName; // ✅ Missing field added

    public PartyCreatedEvent(UUID partyId, UUID tenantId, String kind, String status, String displayName) {
        this.partyId = partyId;
        this.tenantId = tenantId;
        this.kind = kind;
        this.status = status;
        this.displayName = displayName;
    }

    public UUID getPartyId() {
        return partyId;
    }

    public void setPartyId(UUID partyId) {
        this.partyId = partyId;
    }

    public UUID getTenantId() {
        return tenantId;
    }

    public void setTenantId(UUID tenantId) {
        this.tenantId = tenantId;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
