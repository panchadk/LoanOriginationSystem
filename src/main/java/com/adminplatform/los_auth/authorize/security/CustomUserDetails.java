package com.adminplatform.los_auth.authorize.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Map;

public class CustomUserDetails implements UserDetails {

    private String username;
    private String password;
    private Map<String, Object> attributes; // Additional attributes (e.g., tenantId)
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(String username, String password, Map<String, Object> attributes,
                             Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.attributes = attributes;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }
}