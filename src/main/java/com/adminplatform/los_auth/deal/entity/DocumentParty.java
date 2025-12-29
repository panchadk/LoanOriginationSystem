package com.adminplatform.los_auth.deal.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "document_party")
public class DocumentParty {

    @EmbeddedId
    private DocumentPartyId id;

    @Column(name = "signed_at")
    private LocalDateTime signedAt;

    @Column(name = "signer_metadata", columnDefinition = "jsonb")
    private String signerMetadata;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void onCreate() { createdAt = LocalDateTime.now(); }

    // Getters/setters
    public DocumentPartyId getId() { return id; }
    public void setId(DocumentPartyId id) { this.id = id; }
    public LocalDateTime getSignedAt() { return signedAt; }
    public void setSignedAt(LocalDateTime signedAt) { this.signedAt = signedAt; }
    public String getSignerMetadata() { return signerMetadata; }
    public void setSignerMetadata(String signerMetadata) { this.signerMetadata = signerMetadata; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}

@Embeddable
class DocumentPartyId implements Serializable {
    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Column(name = "document_id", nullable = false)
    private UUID documentId;

    @Column(name = "party_id", nullable = false)
    private UUID partyId;

    @Column(name = "role_code", nullable = false, length = 20)
    private String roleCode; // SIGNER/RECIPIENT/PREPARER

    public DocumentPartyId() {}
    public DocumentPartyId(UUID tenantId, UUID documentId, UUID partyId, String roleCode) {
        this.tenantId = tenantId; this.documentId = documentId; this.partyId = partyId; this.roleCode = roleCode;
    }
    public UUID getTenantId() { return tenantId; }
    public void setTenantId(UUID tenantId) { this.tenantId = tenantId; }
    public UUID getDocumentId() { return documentId; }
    public void setDocumentId(UUID documentId) { this.documentId = documentId; }
    public UUID getPartyId() { return partyId; }
    public void setPartyId(UUID partyId) { this.partyId = partyId; }
    public String getRoleCode() { return roleCode; }
    public void setRoleCode(String roleCode) { this.roleCode = roleCode; }

    @Override public boolean equals(Object o) { if (this == o) return true; if (!(o instanceof DocumentPartyId)) return false; DocumentPartyId that = (DocumentPartyId) o; return tenantId.equals(that.tenantId) && documentId.equals(that.documentId) && partyId.equals(that.partyId) && roleCode.equals(that.roleCode); }
    @Override public int hashCode() { return java.util.Objects.hash(tenantId, documentId, partyId, roleCode); }
}
