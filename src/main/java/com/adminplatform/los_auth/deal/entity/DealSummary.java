package com.adminplatform.los_auth.deal.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "deal_summary")
@IdClass(DealSummaryId.class)
public class DealSummary {

    @Id
    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Id
    @Column(name = "deal_id", nullable = false)
    private UUID dealId;

    @Column(name = "stage", nullable = false)
    private String stage;

    @Column(name = "stage_version", nullable = false)
    private int stageVersion;

    @Column(name = "assigned_to")
    private UUID assignedTo;

    @Column(name = "amount_requested", precision = 38, scale = 2)
    private BigDecimal amountRequested;

    @Column(name = "application_date")
    private LocalDate applicationDate;

    @Column(name = "expected_close_date")
    private LocalDate expectedCloseDate;

    @Column(name = "last_decision")
    private String lastDecision;

    @Column(name = "last_updated_at", nullable = false)
    private LocalDateTime lastUpdatedAt;

    // --- Getters & Setters ---
    public UUID getTenantId() { return tenantId; }
    public void setTenantId(UUID tenantId) { this.tenantId = tenantId; }

    public UUID getDealId() { return dealId; }
    public void setDealId(UUID dealId) { this.dealId = dealId; }

    public String getStage() { return stage; }
    public void setStage(String stage) { this.stage = stage; }

    public int getStageVersion() { return stageVersion; }
    public void setStageVersion(int stageVersion) { this.stageVersion = stageVersion; }

    public UUID getAssignedTo() { return assignedTo; }
    public void setAssignedTo(UUID assignedTo) { this.assignedTo = assignedTo; }

    public BigDecimal getAmountRequested() { return amountRequested; }
    public void setAmountRequested(BigDecimal amountRequested) { this.amountRequested = amountRequested; }

    public LocalDate getApplicationDate() { return applicationDate; }
    public void setApplicationDate(LocalDate applicationDate) { this.applicationDate = applicationDate; }

    public LocalDate getExpectedCloseDate() { return expectedCloseDate; }
    public void setExpectedCloseDate(LocalDate expectedCloseDate) { this.expectedCloseDate = expectedCloseDate; }

    public String getLastDecision() { return lastDecision; }
    public void setLastDecision(String lastDecision) { this.lastDecision = lastDecision; }

    public LocalDateTime getLastUpdatedAt() { return lastUpdatedAt; }
    public void setLastUpdatedAt(LocalDateTime lastUpdatedAt) { this.lastUpdatedAt = lastUpdatedAt; }
}
