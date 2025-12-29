package com.adminplatform.los_auth.deal.controller;

import com.adminplatform.los_auth.deal.entity.DealAssignment;
import com.adminplatform.los_auth.deal.service.DealAssignmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/deal/{dealId}/assignments")
public class DealAssignmentController {

    private final DealAssignmentService dealAssignmentService;

    public DealAssignmentController(DealAssignmentService dealAssignmentService) {
        this.dealAssignmentService = dealAssignmentService;
    }

    @PostMapping
    public ResponseEntity<DealAssignment> assignDeal(
            @RequestHeader("X-Tenant-ID") UUID tenantId,
            @PathVariable UUID dealId,
            @RequestParam UUID userId,
            @RequestParam(required = false) UUID queueId,
            @RequestParam(required = false) String reason
    ) {
        return ResponseEntity.ok(
                dealAssignmentService.assignDeal(tenantId, dealId, userId, queueId, reason)
        );
    }

    @GetMapping
    public ResponseEntity<List<DealAssignment>> getOpenAssignments(
            @RequestHeader("X-Tenant-ID") UUID tenantId,
            @PathVariable UUID dealId
    ) {
        return ResponseEntity.ok(dealAssignmentService.getOpenAssignments(tenantId, dealId));
    }
}
