package com.adminplatform.los_auth.party.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "party_address")
public class PartyAddress {

    @EmbeddedId
    private PartyAddressId id;

    @Column(name = "kind")
    private String kind;

    @Column(name = "effective_to")
    private LocalDate effectiveTo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(name = "tenant_id", referencedColumnName = "tenant_id", insertable = false, updatable = false),
            @JoinColumn(name = "address_id", referencedColumnName = "address_id", insertable = false, updatable = false)
    })
    private Address address;

    public PartyAddress() {}

    // Getters and setters

    public PartyAddressId getId() { return id; }
    public void setId(PartyAddressId id) { this.id = id; }

    public String getKind() { return kind; }
    public void setKind(String kind) { this.kind = kind; }

    public LocalDate getEffectiveTo() { return effectiveTo; }
    public void setEffectiveTo(LocalDate effectiveTo) { this.effectiveTo = effectiveTo; }

    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }

    public void setPartyId(UUID partyId) {
        if (this.id == null) {
            this.id = new PartyAddressId();
        }
        this.id.setPartyId(partyId);
    }


}
