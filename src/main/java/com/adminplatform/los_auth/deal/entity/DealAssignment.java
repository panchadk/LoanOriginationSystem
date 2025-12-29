package com.adminplatform.los_auth.deal.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "deal_assignment")
@IdClass(DealAssignmentId.class)
public class DealAssignment {

    @Id
    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Id
    @Column(name = "assignment_id", nullable = false)
    private UUID assignmentId;

    @Column(name = "deal_id", nullable = false)
    private UUID dealId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "queue_id")
    private UUID queueId;

    @Column(name = "assigned_at", nullable = false)
    private LocalDateTime assignedAt;

    @Column(name = "ended_at")
    private LocalDateTime endedAt;

    @Column(name = "reason")
    private String reason;

    // Getters and setters
    public UUID getTenantId() { return tenantId; }
    public void setTenantId(UUID tenantId) { this.tenantId = tenantId; }

    public UUID getAssignmentId() { return assignmentId; }
    public void setAssignmentId(UUID assignmentId) { this.assignmentId = assignmentId; }

    public UUID getDealId() { return dealId; }
    public void setDealId(UUID dealId) { this.dealId = dealId; }

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }

    public UUID getQueueId() { return queueId; }
    public void setQueueId(UUID queueId) { this.queueId = queueId; }

    public LocalDateTime getAssignedAt() { return assignedAt; }
    public void setAssignedAt(LocalDateTime assignedAt) { this.assignedAt = assignedAt; }

    public LocalDateTime getEndedAt() { return endedAt; }
    public void setEndedAt(LocalDateTime endedAt) { this.endedAt = endedAt; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}
