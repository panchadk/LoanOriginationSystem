package com.adminplatform.los_auth.deal.entity;

import com.adminplatform.los_auth.party.entity.Party;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;
@Entity
@Table(name = "borrower")
public class Borrower {

    @Id
    @Column(name = "borrower_id")
    private UUID borrowerId;

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Column(name = "deal_id", nullable = false)
    private UUID dealId;

    @Column(name = "party_id", nullable = false)
    private UUID partyId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_by")
    private UUID createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "tenant_id", referencedColumnName = "tenant_id", insertable = false, updatable = false),
            @JoinColumn(name = "deal_id", referencedColumnName = "deal_id", insertable = false, updatable = false)
    })
    private Deal deal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "tenant_id", referencedColumnName = "tenant_id", insertable = false, updatable = false),
            @JoinColumn(name = "party_id", referencedColumnName = "party_id", insertable = false, updatable = false)
    })
    private Party party;

    // getters and setters...


    public UUID getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(UUID borrowerId) {
        this.borrowerId = borrowerId;
    }

    public UUID getTenantId() {
        return tenantId;
    }

    public void setTenantId(UUID tenantId) {
        this.tenantId = tenantId;
    }

    public UUID getDealId() {
        return dealId;
    }

    public void setDealId(UUID dealId) {
        this.dealId = dealId;
    }

    public UUID getPartyId() {
        return partyId;
    }

    public void setPartyId(UUID partyId) {
        this.partyId = partyId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UUID getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UUID createdBy) {
        this.createdBy = createdBy;
    }

    public Deal getDeal() {
        return deal;
    }

    public void setDeal(Deal deal) {
        this.deal = deal;
    }

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }
}
