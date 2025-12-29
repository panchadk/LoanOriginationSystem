package com.adminplatform.los_auth.authorize.controller;

import com.adminplatform.los_auth.authorize.dto.TenantDto;
import com.adminplatform.los_auth.authorize.entity.Tenant;
import com.adminplatform.los_auth.authorize.repository.TenantRepository;
import com.adminplatform.los_auth.authorize.service.TenantService;
import com.adminplatform.los_auth.authorize.dto.TenantDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class TenantController {

    private final TenantRepository tenantRepository;
    private final TenantService tenantService;

    @Autowired
    public TenantController(TenantRepository tenantRepository, TenantService tenantService) {
        this.tenantRepository = tenantRepository;
        this.tenantService = tenantService;
    }

    // ✅ GET /api/tenants
    @GetMapping("/tenants")
    public List<TenantDto> getTenants() {
        return tenantRepository.findAll().stream()
                .map(t -> new TenantDto(
                        t.getTenantId(),
                        t.getName(),
                        t.getSlug(),
                        t.getStatus(),
                        t.getCreatedBy(),
                        t.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }

    // ✅ POST /api/admin/tenants
    @PostMapping("/admin/tenants")
    public ResponseEntity<?> createTenant(@RequestBody TenantDto request) {
        try {
            Tenant tenant = tenantService.createTenant(
                    request.getName(),
                    request.getSlug(),
                    request.getCreatedBy()
            );
            return ResponseEntity.ok(tenant);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Tenant creation failed.");
        }
    }
}
