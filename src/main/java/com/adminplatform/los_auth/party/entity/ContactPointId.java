package com.adminplatform.los_auth.party.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class ContactPointId implements Serializable {

    @Column(name = "tenant_id")
    private UUID tenantId;

    @Column(name = "contact_id")
    private UUID contactId;

    public ContactPointId() {}

    public ContactPointId(UUID tenantId, UUID contactId) {
        this.tenantId = tenantId;
        this.contactId = contactId;
    }

    public UUID getTenantId() {
        return tenantId;
    }

    public void setTenantId(UUID tenantId) {
        this.tenantId = tenantId;
    }

    public UUID getContactId() {
        return contactId;
    }

    public void setContactId(UUID contactId) {
        this.contactId = contactId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContactPointId)) return false;
        ContactPointId that = (ContactPointId) o;
        return Objects.equals(tenantId, that.tenantId) &&
                Objects.equals(contactId, that.contactId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tenantId, contactId);
    }
}
