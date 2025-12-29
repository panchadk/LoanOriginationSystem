package com.adminplatform.los_auth.deal.controller;

import com.adminplatform.los_auth.deal.entity.DealSummary;
import com.adminplatform.los_auth.deal.service.DealSummaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/deal/summaries")
public class DealSummaryController {

    private final DealSummaryService dealSummaryService;

    public DealSummaryController(DealSummaryService dealSummaryService) {
        this.dealSummaryService = dealSummaryService;
    }

    @GetMapping
    public ResponseEntity<List<DealSummary>> getSummaries(
            @RequestHeader("X-Tenant-ID") UUID tenantId,
            @RequestParam(required = false) String stage
    ) {
        if (stage != null) {
            return ResponseEntity.ok(dealSummaryService.getSummariesByStage(tenantId, stage));
        }
        return ResponseEntity.ok(dealSummaryService.getSummaries(tenantId));
    }

    @GetMapping("/{dealId}")
    public ResponseEntity<DealSummary> getSummary(
            @RequestHeader("X-Tenant-ID") UUID tenantId,
            @PathVariable UUID dealId
    ) {
        return dealSummaryService.getSummary(tenantId, dealId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
