package com.adminplatform.los_auth.deal.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "deal")
@IdClass(DealId.class)
public class Deal {

    @Id
    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "deal_id", nullable = false, updatable = false)
    private UUID dealId;

    @Column(name = "stage", nullable = false)
    private String stage;

    @Column(name = "stage_entered_at", nullable = false)
    private LocalDateTime stageEnteredAt;

    @Column(name = "stage_version", nullable = false)
    private int stageVersion;

    @Column(name = "assigned_to_user_id")
    private UUID assignedToUserId;

    @Column(name = "broker_ref")
    private String brokerRef;

    @Column(name = "source")
    private String source;

    @Column(name = "referral_code")
    private String referralCode;

    @Column(name = "product_code")
    private String productCode;

    @Column(name = "amount_requested")
    private BigDecimal amountRequested;

    @Column(name = "borrower_name")
    private String borrowerName;

    @Column(name = "application_date")
    private LocalDate applicationDate;

    @Column(name = "expected_close_date")
    private LocalDate expectedCloseDate;

    @Column(name = "created_by", nullable = false)
    private UUID createdBy;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // --- Lifecycle hook to auto-populate defaults ---
    @PrePersist
    public void prePersist() {
        if (dealId == null) {
            dealId = UUID.randomUUID();
        }
        if (stageEnteredAt == null) {
            stageEnteredAt = LocalDateTime.now();
        }
        if (stageVersion == 0) {
            stageVersion = 1;
        }
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        // ⚠️ createdBy must be set in service/controller, not here
    }

    // --- Getters & Setters ---
    public UUID getTenantId() { return tenantId; }
    public void setTenantId(UUID tenantId) { this.tenantId = tenantId; }

    public UUID getDealId() { return dealId; }
    public void setDealId(UUID dealId) { this.dealId = dealId; }

    public String getStage() { return stage; }
    public void setStage(String stage) { this.stage = stage; }

    public LocalDateTime getStageEnteredAt() { return stageEnteredAt; }
    public void setStageEnteredAt(LocalDateTime stageEnteredAt) { this.stageEnteredAt = stageEnteredAt; }

    public int getStageVersion() { return stageVersion; }
    public void setStageVersion(int stageVersion) { this.stageVersion = stageVersion; }

    public UUID getAssignedToUserId() { return assignedToUserId; }
    public void setAssignedToUserId(UUID assignedToUserId) { this.assignedToUserId = assignedToUserId; }

    public String getBrokerRef() { return brokerRef; }
    public void setBrokerRef(String brokerRef) { this.brokerRef = brokerRef; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public String getReferralCode() { return referralCode; }
    public void setReferralCode(String referralCode) { this.referralCode = referralCode; }

    public String getProductCode() { return productCode; }
    public void setProductCode(String productCode) { this.productCode = productCode; }

    public BigDecimal getAmountRequested() { return amountRequested; }
    public void setAmountRequested(BigDecimal amountRequested) { this.amountRequested = amountRequested; }

    public String getBorrowerName() { return borrowerName; }
    public void setBorrowerName(String borrowerName) { this.borrowerName = borrowerName; }

    public LocalDate getApplicationDate() { return applicationDate; }
    public void setApplicationDate(LocalDate applicationDate) { this.applicationDate = applicationDate; }

    public LocalDate getExpectedCloseDate() { return expectedCloseDate; }
    public void setExpectedCloseDate(LocalDate expectedCloseDate) { this.expectedCloseDate = expectedCloseDate; }

    public UUID getCreatedBy() { return createdBy; }
    public void setCreatedBy(UUID createdBy) { this.createdBy = createdBy; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }



    // --- Business logic stubs ---
    public boolean isAllPtdSatisfied() { return false; }
    public boolean isAppraisalAccepted() { return false; }
    public boolean isExceptionsApproved() { return false; }
    public boolean isPricingResolved() { return false; }
    public boolean isAllPtfSatisfied() { return false; }
    public boolean isDepositCleared() { return false; }
    public int getParticipationPercent() { return 0; }
    public boolean hasValidTitleInsurance() { return false; }
    public boolean hasValidPropertyInsurance() { return false; }
    public boolean hasBalancedFundingSettlement() { return false; }
    public boolean isLoanContractComputed() { return false; }


    public static class VerificationRecord {
        private String type;
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
    }

    public static class Commitment {
        private String status;

        public Commitment() {}

        public Commitment(String status) {
            this.status = status;
        }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }

}
