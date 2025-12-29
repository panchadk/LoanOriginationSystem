package com.adminplatform.los_auth.party.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "party_bank_account")
public class PartyBankAccount {

    @Id
    @Column(name = "bank_account_id")
    private UUID bankAccountId;

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Column(name = "party_id", nullable = false)
    private UUID partyId;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "routing_number_hash")
    private String routingNumberHash;

    @Column(name = "account_no_hash")
    private String accountNoHash;

    @Column(name = "account_mask_last4")
    private String accountMaskLast4;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "is_default")
    private Boolean isDefault;

    @Column(name = "verification_status")
    private String verificationStatus;

    @Column(name = "verification_method")
    private String verificationMethod;

    @Column(name = "verified_at")
    private LocalDateTime verifiedAt;

    @Column(name = "consent_to_debit")
    private Boolean consentToDebit;

    @Column(name = "mandate_id")
    private UUID mandateId;

    @Column(name = "mandate_signed_at")
    private LocalDateTime mandateSignedAt;

    @Column(name = "debit_daily_limit")
    private BigDecimal debitDailyLimit;

    @Column(name = "effective_from")
    private LocalDate effectiveFrom;

    @Column(name = "effective_to")
    private LocalDate effectiveTo;

    @Column(name = "notes")
    private String notes;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public UUID getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(UUID bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public UUID getTenantId() {
        return tenantId;
    }

    public void setTenantId(UUID tenantId) {
        this.tenantId = tenantId;
    }

    public UUID getPartyId() {
        return partyId;
    }

    public void setPartyId(UUID partyId) {
        this.partyId = partyId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getRoutingNumberHash() {
        return routingNumberHash;
    }

    public void setRoutingNumberHash(String routingNumberHash) {
        this.routingNumberHash = routingNumberHash;
    }

    public String getAccountNoHash() {
        return accountNoHash;
    }

    public void setAccountNoHash(String accountNoHash) {
        this.accountNoHash = accountNoHash;
    }

    public String getAccountMaskLast4() {
        return accountMaskLast4;
    }

    public void setAccountMaskLast4(String accountMaskLast4) {
        this.accountMaskLast4 = accountMaskLast4;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public String getVerificationMethod() {
        return verificationMethod;
    }

    public void setVerificationMethod(String verificationMethod) {
        this.verificationMethod = verificationMethod;
    }

    public LocalDateTime getVerifiedAt() {
        return verifiedAt;
    }

    public void setVerifiedAt(LocalDateTime verifiedAt) {
        this.verifiedAt = verifiedAt;
    }

    public Boolean getConsentToDebit() {
        return consentToDebit;
    }

    public void setConsentToDebit(Boolean consentToDebit) {
        this.consentToDebit = consentToDebit;
    }

    public UUID getMandateId() {
        return mandateId;
    }

    public void setMandateId(UUID mandateId) {
        this.mandateId = mandateId;
    }

    public LocalDateTime getMandateSignedAt() {
        return mandateSignedAt;
    }

    public void setMandateSignedAt(LocalDateTime mandateSignedAt) {
        this.mandateSignedAt = mandateSignedAt;
    }

    public BigDecimal getDebitDailyLimit() {
        return debitDailyLimit;
    }

    public void setDebitDailyLimit(BigDecimal debitDailyLimit) {
        this.debitDailyLimit = debitDailyLimit;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public PartyBankAccount() {}

    // Getters and setters omitted for brevity
}
