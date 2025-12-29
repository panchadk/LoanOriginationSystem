package com.adminplatform.los_auth.deal.controller;

import com.adminplatform.los_auth.deal.entity.DealTag;
import com.adminplatform.los_auth.deal.service.DealTagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/deal/{dealId}/tags")
public class DealTagController {

    private final DealTagService dealTagService;

    public DealTagController(DealTagService dealTagService) {
        this.dealTagService = dealTagService;
    }

    @PostMapping
    public ResponseEntity<DealTag> addTag(
            @RequestHeader("X-Tenant-ID") UUID tenantId,
            @PathVariable UUID dealId,
            @RequestParam String tag,
            @RequestHeader("X-User-ID") UUID createdBy
    ) {
        return ResponseEntity.ok(dealTagService.addTag(tenantId, dealId, tag, createdBy));
    }

    @GetMapping
    public ResponseEntity<List<DealTag>> getTags(
            @RequestHeader("X-Tenant-ID") UUID tenantId,
            @PathVariable UUID dealId
    ) {
        return ResponseEntity.ok(dealTagService.getTags(tenantId, dealId));
    }
}
