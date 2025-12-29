package com.adminplatform.los_auth.deal.controller;

import com.adminplatform.los_auth.deal.entity.DealDecision;
import com.adminplatform.los_auth.deal.service.DealDecisionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/deal/{dealId}/decisions")
public class DealDecisionController {

    private final DealDecisionService dealDecisionService;

    public DealDecisionController(DealDecisionService dealDecisionService) {
        this.dealDecisionService = dealDecisionService;
    }

    @PostMapping
    public ResponseEntity<DealDecision> recordDecision(
            @RequestHeader("X-Tenant-ID") UUID tenantId,
            @PathVariable UUID dealId,
            @RequestHeader("X-User-ID") UUID decidedBy,
            @RequestParam String type,
            @RequestParam(required = false) String reasonCode,
            @RequestParam(required = false) String notes
    ) {
        return ResponseEntity.ok(
                dealDecisionService.recordDecision(tenantId, dealId, type, reasonCode, notes, decidedBy)
        );
    }

    @GetMapping
    public ResponseEntity<List<DealDecision>> getDecisions(
            @RequestHeader("X-Tenant-ID") UUID tenantId,
            @PathVariable UUID dealId
    ) {
        return ResponseEntity.ok(dealDecisionService.getDecisions(tenantId, dealId));
    }
}
