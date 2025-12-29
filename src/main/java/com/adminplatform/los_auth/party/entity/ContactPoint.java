package com.adminplatform.los_auth.party.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "contact_point")
public class ContactPoint {

    @EmbeddedId
    private ContactPointId id;

    @Column(name = "type")
    private String type; // EMAIL, MOBILE, PHONE, etc.

    @Column(name = "value", nullable = false)
    private String value;

    @Column(name = "verified")
    private Boolean verified;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public ContactPoint() {
    }

    public ContactPointId getId() {
        return id;
    }

    public void setId(ContactPointId id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}