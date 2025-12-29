package com.adminplatform.los_auth.authorize.dto;

import java.util.List;
import java.util.UUID;

public class RegisterRequest {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private UUID tenantId; // ✅ Correct type
    private List<UUID> roles;
    private String username;
    private String tenantSlug;

    // Getters
    public String getTenantSlug() {return tenantSlug;}
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public UUID getTenantId() { return tenantId; }
    public List<UUID> getRoles() {
        return roles;
    }
    public String getUsername() {
        return username;
    }

    // Setters
    public void setTenantSlug(String tenantSlug) {this.tenantSlug = tenantSlug;}
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setTenantId(UUID tenantId) { this.tenantId = tenantId; }
    public void setRoles(List<UUID> roles) {
        this.roles = roles;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}