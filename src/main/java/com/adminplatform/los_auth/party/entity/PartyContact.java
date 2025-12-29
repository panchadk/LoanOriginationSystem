package com.adminplatform.los_auth.party.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "party_contact")
public class PartyContact {

    @EmbeddedId
    private PartyContactId id;

    @Column(name = "type")
    private String type;


    @Column(name = "effective_to")
    private LocalDate effectiveTo;

    @Column(name = "preferred")
    private boolean preferred;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(name = "tenant_id", referencedColumnName = "tenant_id", insertable = false, updatable = false),
            @JoinColumn(name = "contact_id", referencedColumnName = "contact_id", insertable = false, updatable = false)
    })
    private ContactPoint contactPoint;

    public PartyContact() {}

    // Getters and setters

    public PartyContactId getId() {
        return id;
    }

    public void setId(PartyContactId id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getEffectiveTo() {
        return effectiveTo;
    }

    public void setEffectiveTo(LocalDate effectiveTo) {
        this.effectiveTo = effectiveTo;
    }

    public ContactPoint getContactPoint() {
        return contactPoint;
    }

    public void setContactPoint(ContactPoint contactPoint) {
        this.contactPoint = contactPoint;
    }

    public LocalDate getEffectiveFrom() {
        return id != null ? id.getEffectiveFrom() : null;
    }

    public boolean isPreferred() {
        return preferred;
    }

    public void setPreferred(boolean preferred) {
        this.preferred = preferred;
    }
}
