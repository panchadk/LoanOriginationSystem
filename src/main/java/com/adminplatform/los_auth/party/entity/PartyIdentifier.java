package com.adminplatform.los_auth.party.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "party_identifier")
public class PartyIdentifier {

    @Id
    @Column(name = "identifier_id")
    private UUID identifierId;

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Column(name = "party_id", nullable = false)
    private UUID partyId;

    @Column(name = "type_code", nullable = false)
    private String typeCode;

    @Column(name = "value_ciphertext", nullable = false)
    private String valueCiphertext;

    @Column(name = "last4")
    private String last4;

    public UUID getIdentifierId() {
        return identifierId;
    }

    public void setIdentifierId(UUID identifierId) {
        this.identifierId = identifierId;
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

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getValueCiphertext() {
        return valueCiphertext;
    }

    public void setValueCiphertext(String valueCiphertext) {
        this.valueCiphertext = valueCiphertext;
    }

    public String getLast4() {
        return last4;
    }

    public void setLast4(String last4) {
        this.last4 = last4;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }

    @Column(name = "issuer")
    private String issuer;

    @Column(name = "valid_from")
    private LocalDate validFrom;

    @Column(name = "valid_to")
    private LocalDate validTo;

    public PartyIdentifier() {}

    // Getters and setters omitted for brevity — include them or use Lombok
}
