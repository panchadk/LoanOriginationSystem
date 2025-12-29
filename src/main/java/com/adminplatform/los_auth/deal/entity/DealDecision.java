package com.adminplatform.los_auth.deal.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "deal_decision")
@IdClass(DealDecisionId.class)
public class DealDecision {

    @Id
    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Id
    @Column(name = "decision_id", nullable = false)
    private UUID decisionId;

    @Column(name = "deal_id", nullable = false)
    private UUID dealId;

    @Column(name = "type", nullable = false)
    private String type; // APPROVE, CONDITIONAL, DECLINE, WITHDRAW, CANCEL

    @Column(name = "reason_code")
    private String reasonCode;

    @Column(name = "notes")
    private String notes;

    @Column(name = "decided_by", nullable = false)
    private UUID decidedBy;

    @Column(name = "decided_at", nullable = false)
    private LocalDateTime decidedAt;

    // Getters and setters
    public UUID getTenantId() { return tenantId; }
    public void setTenantId(UUID tenantId) { this.tenantId = tenantId; }

    public UUID getDecisionId() { return decisionId; }
    public void setDecisionId(UUID decisionId) { this.decisionId = decisionId; }

    public UUID getDealId() { return dealId; }
    public void setDealId(UUID dealId) { this.dealId = dealId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getReasonCode() { return reasonCode; }
    public void setReasonCode(String reasonCode) { this.reasonCode = reasonCode; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public UUID getDecidedBy() { return decidedBy; }
    public void setDecidedBy(UUID decidedBy) { this.decidedBy = decidedBy; }

    public LocalDateTime getDecidedAt() { return decidedAt; }
    public void setDecidedAt(LocalDateTime decidedAt) { this.decidedAt = decidedAt; }
}
