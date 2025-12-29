package com.adminplatform.los_auth.authorize.dto;

import com.adminplatform.los_auth.authorize.model.RoleMaster;

import java.util.List;

public class RoleDto {
    private String roleId;
    private String name;
    private String tenantId; // ✅ Add this field

    public RoleDto(String roleId, String name, String tenantId) {
        this.roleId = roleId;
        this.name = name;
        this.tenantId = tenantId;
    }

    public String getRoleId() {
        return roleId;
    }

    public String getName() {
        return name;
    }

    public String getTenantId() {
        return tenantId;
    }
}
