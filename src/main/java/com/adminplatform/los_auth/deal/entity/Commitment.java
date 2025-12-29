package com.adminplatform.los_auth.deal.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "commitment")
public class Commitment {

    @EmbeddedId
    private CommitmentId id;

    @Column(name = "deal_id", nullable = false)
    private UUID dealId;

    @Column(name = "version", nullable = false)
    private Integer version;

    @Column(name = "status", nullable = false, length = 20)
    private String status; // OUT/SIGNED/EXPIRED

    @Column(name = "terms_snapshot", columnDefinition = "jsonb", nullable = false)
    private String termsSnapshot;

    @Column(name = "issued_at")
    private LocalDateTime issuedAt;

    @Column(name = "signed_at")
    private LocalDateTime signedAt;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "rescinded_at")
    private LocalDateTime rescindedAt;

    @Column(name = "rescinded_by")
    private UUID rescindedBy;

    @Column(name = "document_id")
    private UUID documentId;

    @Column(name = "pricing_source", nullable = false, length = 20)
    private String pricingSource; // ENGINE/MANUAL

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    void onCreate() { createdAt = LocalDateTime.now(); updatedAt = createdAt; }
    @PreUpdate
    void onUpdate() { updatedAt = LocalDateTime.now(); }

    // Getters/setters
    public CommitmentId getId() { return id; }
    public void setId(CommitmentId id) { this.id = id; }
    public UUID getDealId() { return dealId; }
    public void setDealId(UUID dealId) { this.dealId = dealId; }
    public Integer getVersion() { return version; }
    public void setVersion(Integer version) { this.version = version; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getTermsSnapshot() { return termsSnapshot; }
    public void setTermsSnapshot(String termsSnapshot) { this.termsSnapshot = termsSnapshot; }
    public LocalDateTime getIssuedAt() { return issuedAt; }
    public void setIssuedAt(LocalDateTime issuedAt) { this.issuedAt = issuedAt; }
    public LocalDateTime getSignedAt() { return signedAt; }
    public void setSignedAt(LocalDateTime signedAt) { this.signedAt = signedAt; }
    public LocalDateTime getExpiresAt() { return expiresAt; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }
    public LocalDateTime getRescindedAt() { return rescindedAt; }
    public void setRescindedAt(LocalDateTime rescindedAt) { this.rescindedAt = rescindedAt; }
    public UUID getRescindedBy() { return rescindedBy; }
    public void setRescindedBy(UUID rescindedBy) { this.rescindedBy = rescindedBy; }
    public UUID getDocumentId() { return documentId; }
    public void setDocumentId(UUID documentId) { this.documentId = documentId; }
    public String getPricingSource() { return pricingSource; }
    public void setPricingSource(String pricingSource) { this.pricingSource = pricingSource; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}

@Embeddable
class CommitmentId implements Serializable {
    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Column(name = "commitment_id", nullable = false)
    private UUID commitmentId;

    public CommitmentId() {}
    public CommitmentId(UUID tenantId, UUID commitmentId) { this.tenantId = tenantId; this.commitmentId = commitmentId; }
    public UUID getTenantId() { return tenantId; }
    public void setTenantId(UUID tenantId) { this.tenantId = tenantId; }
    public UUID getCommitmentId() { return commitmentId; }
    public void setCommitmentId(UUID commitmentId) { this.commitmentId = commitmentId; }

    @Override public boolean equals(Object o) { if (this == o) return true; if (!(o instanceof CommitmentId)) return false; CommitmentId that = (CommitmentId) o; return tenantId.equals(that.tenantId) && commitmentId.equals(that.commitmentId); }
    @Override public int hashCode() { return java.util.Objects.hash(tenantId, commitmentId); }
}
