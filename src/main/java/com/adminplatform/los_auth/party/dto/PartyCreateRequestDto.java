package com.adminplatform.los_auth.party.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.util.UUID;

public class PartyCreateRequestDto {

    @NotNull(message = "kind is required")
    @Pattern(regexp = "PERSON|ORGANIZATION", message = "kind must be either PERSON or ORG")
    private String kind;

    // Optional: remove if tenantId is passed only via header
    private UUID tenantId;

    // Person fields
    @Size(max = 100, message = "givenName must be at most 100 characters")
    private String givenName;

    @Size(max = 100, message = "familyName must be at most 100 characters")
    private String familyName;

    @Size(max = 100, message = "middleName must be at most 100 characters")
    private String middleName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dob;

    @Size(max = 50, message = "residencyStatus must be at most 50 characters")
    private String residencyStatus;

    // Organization fields
    @Size(max = 200, message = "legalName must be at most 200 characters")
    private String legalName;

    @Size(max = 100, message = "registrationJurisdiction must be at most 100 characters")
    private String registrationJurisdiction;

    @Size(max = 100, message = "businessType must be at most 100 characters")
    private String businessType;

    @Size(max = 50, message = "bin must be at most 50 characters")
    private String bin;

    public PartyCreateRequestDto() {
        System.out.println("✅ PartyCreateRequestDto initialized");
    }

    // Getters and Setters

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public UUID getTenantId() {
        return tenantId;
    }

    public void setTenantId(UUID tenantId) {
        this.tenantId = tenantId;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getResidencyStatus() {
        return residencyStatus;
    }

    public void setResidencyStatus(String residencyStatus) {
        this.residencyStatus = residencyStatus;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getRegistrationJurisdiction() {
        return registrationJurisdiction;
    }

    public void setRegistrationJurisdiction(String registrationJurisdiction) {
        this.registrationJurisdiction = registrationJurisdiction;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }
}
