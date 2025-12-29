package com.adminplatform.los_auth.deal.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "brokers")
public class Broker {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Column(name = "deal_id", nullable = false)
    private UUID dealId;

    private String name;
    private String company;

    @Column(name = "created_by", nullable = false)
    private UUID createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "tenant_id", referencedColumnName = "tenant_id", insertable = false, updatable = false),
            @JoinColumn(name = "deal_id", referencedColumnName = "deal_id", insertable = false, updatable = false)
    })
    private Deal deal;

    // --- Getters & Setters ---
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getTenantId() { return tenantId; }
    public void setTenantId(UUID tenantId) { this.tenantId = tenantId; }

    public UUID getDealId() { return dealId; }
    public void setDealId(UUID dealId) { this.dealId = dealId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }

    public UUID getCreatedBy() { return createdBy; }
    public void setCreatedBy(UUID createdBy) { this.createdBy = createdBy; }

    public Deal getDeal() { return deal; }
    public void setDeal(Deal deal) { this.deal = deal; }
}
