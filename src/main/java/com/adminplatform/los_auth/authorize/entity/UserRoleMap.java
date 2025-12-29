package com.adminplatform.los_auth.authorize.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "user_role_map")
public class UserRoleMap {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantid;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "role_id", nullable = false)
    private UUID roleId;

    // Constructors
    public UserRoleMap() {
    }

    public UserRoleMap(UUID userId, UUID tenantid, UUID roleId) {
        this.userId = userId;
        this.roleId = roleId;
        this.tenantid=tenantid;
    }

    // Getters and setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getTenantid() {
        return tenantid;
    }

    public void setTenantid(UUID tenantid) {
        this.tenantid = tenantid;
    }


    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getRoleId() {
        return roleId;
    }

    public void setRoleId(UUID roleId) {
        this.roleId = roleId;
    }

    // Optional: equals and hashCode if needed for collections or comparisons
}
