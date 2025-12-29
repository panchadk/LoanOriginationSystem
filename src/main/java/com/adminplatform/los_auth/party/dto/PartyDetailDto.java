package com.adminplatform.los_auth.party.dto;

import java.time.Instant;
import java.util.UUID;

public class PartyDetailDto {

    private UUID partyId;
    private String kind;
    private String status;
    private Instant createdAt;

    // Person fields
    private String givenName;
    private String familyName;
    private String middleName;
    private String residencyStatus;
    private String dob;

    // Organization fields
    private String legalName;
    private String registrationJurisdiction;
    private String businessType;
    private String bin;

    // Getters and setters

    public UUID getPartyId() {
        return partyId;
    }

    public void setPartyId(UUID partyId) {
        this.partyId = partyId;
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

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
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

    public String getResidencyStatus() {
        return residencyStatus;
    }

    public void setResidencyStatus(String residencyStatus) {
        this.residencyStatus = residencyStatus;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
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
