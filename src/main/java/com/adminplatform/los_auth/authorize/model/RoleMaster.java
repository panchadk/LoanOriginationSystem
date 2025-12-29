package com.adminplatform.los_auth.authorize.model;

import com.adminplatform.los_auth.authorize.entity.Tenant;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "role_master")
public class RoleMaster {

    @Id
    @Column(name = "role_id")
    private UUID roleId;

    @Column(name = "role_name")
    private String roleName;

    @ManyToOne
    @JoinColumn(name = "tenant_id") // ✅ This must match your DB column name
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

    // ✅ Add this to expose tenantId in JSON
    @JsonProperty("tenantId")
    public UUID getTenantId() {
        return tenant != null ? tenant.getTenantId() : null;
    }
}
