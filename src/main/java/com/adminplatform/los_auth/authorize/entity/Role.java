package com.adminplatform.los_auth.authorize.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "role_master")
public class Role {

    @Id
    @Column(name = "role_id")
    private UUID roleId;

    @Column(name = "role_name")
    private String roleName;

    @ManyToOne
    @JoinColumn(name = "tenant_id", nullable = false) // ✅ Maps to tenant_id column
    private Tenant tenant;

    // Getters and setters

    public UUID getRoleId() {
        return roleId;
    }

    public void setRoleId(UUID roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }
}
