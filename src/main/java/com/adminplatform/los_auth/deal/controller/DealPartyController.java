package com.adminplatform.los_auth.deal.controller;

import com.adminplatform.los_auth.deal.dto.DealPartyRequestDto;
import com.adminplatform.los_auth.deal.entity.DealParty;
import com.adminplatform.los_auth.deal.service.DealPartyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/deal/{dealId}/party")
public class DealPartyController {

    private final DealPartyService dealPartyService;

    public DealPartyController(DealPartyService dealPartyService) {
        this.dealPartyService = dealPartyService;
    }

    @PostMapping
    public ResponseEntity<Void> assignPartyToDeal(
            @RequestHeader("X-Tenant-ID") UUID tenantId,
            @PathVariable UUID dealId,
            @RequestBody DealPartyRequestDto request
    ) {
        dealPartyService.assignParty(tenantId, dealId, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<DealParty>> getDealParties(
            @RequestHeader("X-Tenant-ID") UUID tenantId,
            @PathVariable UUID dealId
    ) {
        return ResponseEntity.ok(dealPartyService.getPartiesForDeal(tenantId, dealId));
    }
}
