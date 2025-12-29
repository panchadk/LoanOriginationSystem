package com.adminplatform.los_auth.deal.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "property")
public class Property {

    @EmbeddedId
    private PropertyId id;

    @Column(name = "address_id", nullable = false)
    private UUID addressId;

    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @Column(name = "occupancy", nullable = false, length = 30)
    private String occupancy;

    // Store JSONB as text; map to JSON if needed via converter
    @Column(name = "characteristics", columnDefinition = "jsonb", nullable = false)
    private String characteristics = "{}";

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getters/setters
    public PropertyId getId() { return id; }
    public void setId(PropertyId id) { this.id = id; }
    public UUID getAddressId() { return addressId; }
    public void setAddressId(UUID addressId) { this.addressId = addressId; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getOccupancy() { return occupancy; }
    public void setOccupancy(String occupancy) { this.occupancy = occupancy; }
    public String getCharacteristics() { return characteristics; }
    public void setCharacteristics(String characteristics) { this.characteristics = characteristics; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}

@Embeddable
class PropertyId implements Serializable {
    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Column(name = "property_id", nullable = false)
    private UUID propertyId;

    public PropertyId() {}
    public PropertyId(UUID tenantId, UUID propertyId) {
        this.tenantId = tenantId;
        this.propertyId = propertyId;
    }
    public UUID getTenantId() { return tenantId; }
    public void setTenantId(UUID tenantId) { this.tenantId = tenantId; }
    public UUID getPropertyId() { return propertyId; }
    public void setPropertyId(UUID propertyId) { this.propertyId = propertyId; }

    @Override public boolean equals(Object o) { if (this == o) return true; if (!(o instanceof PropertyId)) return false; PropertyId that = (PropertyId) o; return tenantId.equals(that.tenantId) && propertyId.equals(that.propertyId); }
    @Override public int hashCode() { return java.util.Objects.hash(tenantId, propertyId); }
}
