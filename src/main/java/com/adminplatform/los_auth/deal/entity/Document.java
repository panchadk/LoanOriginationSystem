package com.adminplatform.los_auth.deal.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "document")
public class Document {

    @EmbeddedId
    private DocumentId id;

    @Column(name = "owner_type", nullable = false, length = 30)
    private String ownerType; // DEAL/PARTY/PROPERTY/APPRAISAL/COMMITMENT

    @Column(name = "owner_id", nullable = false)
    private UUID ownerId;

    @Column(name = "doc_type", nullable = false, length = 30)
    private String docType; // ID/APPRAISAL/INSURANCE/COMMITMENT/LOI/CHARGE/GUARANTEE/OTHER

    @Column(name = "status", nullable = false, length = 20)
    private String status; // DRAFT/OUT/SIGNED/EXPIRED

    @Column(name = "visibility", nullable = false, length = 20)
    private String visibility; // INTERNAL/BROKER/EXTERNAL

    @Column(name = "upload_at", nullable = false)
    private LocalDateTime uploadAt;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "sha256", length = 64)
    private String sha256;

    @Column(name = "storage_key", nullable = false, length = 255)
    private String storageKey;

    @Column(name = "legal_hold", nullable = false)
    private Boolean legalHold = false;

    @Column(name = "retention_class", length = 50)
    private String retentionClass;

    @Column(name = "purge_at")
    private LocalDateTime purgeAt;

    @Column(name = "attributes", columnDefinition = "jsonb", nullable = false)
    private String attributes = "{}";

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    void onCreate() { uploadAt = LocalDateTime.now(); createdAt = uploadAt; updatedAt = uploadAt; }
    @PreUpdate
    void onUpdate() { updatedAt = LocalDateTime.now(); }

    // Getters/setters
    public DocumentId getId() { return id; }
    public void setId(DocumentId id) { this.id = id; }
    public String getOwnerType() { return ownerType; }
    public void setOwnerType(String ownerType) { this.ownerType = ownerType; }
    public UUID getOwnerId() { return ownerId; }
    public void setOwnerId(UUID ownerId) { this.ownerId = ownerId; }
    public String getDocType() { return docType; }
    public void setDocType(String docType) { this.docType = docType; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getVisibility() { return visibility; }
    public void setVisibility(String visibility) { this.visibility = visibility; }
    public LocalDateTime getUploadAt() { return uploadAt; }
    public void setUploadAt(LocalDateTime uploadAt) { this.uploadAt = uploadAt; }
    public LocalDateTime getExpiresAt() { return expiresAt; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }
    public String getSha256() { return sha256; }
    public void setSha256(String sha256) { this.sha256 = sha256; }
    public String getStorageKey() { return storageKey; }
    public void setStorageKey(String storageKey) { this.storageKey = storageKey; }
    public Boolean getLegalHold() { return legalHold; }
    public void setLegalHold(Boolean legalHold) { this.legalHold = legalHold; }
    public String getRetentionClass() { return retentionClass; }
    public void setRetentionClass(String retentionClass) { this.retentionClass = retentionClass; }
    public LocalDateTime getPurgeAt() { return purgeAt; }
    public void setPurgeAt(LocalDateTime purgeAt) { this.purgeAt = purgeAt; }
    public String getAttributes() { return attributes; }
    public void setAttributes(String attributes) { this.attributes = attributes; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}

@Embeddable
class DocumentId implements Serializable {
    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Column(name = "document_id", nullable = false)
    private UUID documentId;

    public DocumentId() {}
    public DocumentId(UUID tenantId, UUID documentId) { this.tenantId = tenantId; this.documentId = documentId; }
    public UUID getTenantId() { return tenantId; }
    public void setTenantId(UUID tenantId) { this.tenantId = tenantId; }
    public UUID getDocumentId() { return documentId; }
    public void setDocumentId(UUID documentId) { this.documentId = documentId; }

    @Override public boolean equals(Object o) { if (this == o) return true; if (!(o instanceof DocumentId)) return false; DocumentId that = (DocumentId) o; return tenantId.equals(that.tenantId) && documentId.equals(that.documentId); }
    @Override public int hashCode() { return java.util.Objects.hash(tenantId, documentId); }
}
