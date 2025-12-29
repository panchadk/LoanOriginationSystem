package com.adminplatform.los_auth.authorize.service;

import com.adminplatform.los_auth.authorize.entity.Tenant;
import com.adminplatform.los_auth.authorize.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TenantService {

    @Autowired
    private TenantRepository tenantRepository;

    public Tenant createTenant(String name, String slug, String createdBy) {
        if (tenantRepository.existsBySlug(slug)) {
            throw new IllegalArgumentException("Slug already exists.");
        }

        Tenant tenant = new Tenant();
        tenant.setName(name);
        tenant.setSlug(slug.toLowerCase());
        tenant.setStatus("ACTIVE");
        tenant.setCreatedAt(LocalDateTime.now());
        tenant.setCreatedBy(createdBy);

        return tenantRepository.save(tenant);
    }
}