package com.adminplatform.los_auth.authorize.dto;

import java.util.List;
import java.util.UUID;

public class UserDto {
    private UUID id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String status;
    private UUID tenantId;
    private String tenantSlug;
    private List<UUID> roles;

    // Constructor
    public UserDto(UUID id, String username, String email, String firstName, String lastName,
                   String status, UUID tenantId, String tenantSlug, List<UUID> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
        this.tenantId = tenantId;
        this.tenantSlug = tenantSlug;
        this.roles = roles;
    }

    // Getters and setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public UUID getTenantId() { return tenantId; }
    public void setTenantId(UUID tenantId) { this.tenantId = tenantId; }

    public String getTenantSlug() { return tenantSlug; }
    public void setTenantSlug(String tenantSlug) { this.tenantSlug = tenantSlug; }

    public List<UUID> getRoles() { return roles; }
    public void setRoles(List<UUID> roles) { this.roles = roles; }
}
