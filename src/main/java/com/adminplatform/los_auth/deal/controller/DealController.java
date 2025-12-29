package com.adminplatform.los_auth.deal.controller;

import com.adminplatform.los_auth.deal.dto.DealSummaryDto;
import com.adminplatform.los_auth.deal.entity.Deal;
import com.adminplatform.los_auth.deal.entity.Borrower;
import com.adminplatform.los_auth.deal.entity.Broker;
import com.adminplatform.los_auth.deal.entity.Guarantor;
import com.adminplatform.los_auth.deal.service.DealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/deal")
public class DealController {

    @Autowired
    private DealService dealService;

    // List all deals for a tenant
    @GetMapping
    public List<Deal> listDeals(@RequestHeader("X-Tenant-ID") UUID tenantId) {
        return dealService.getDeals(tenantId);
    }

    // Get a single deal by tenant + dealId
    @GetMapping("/{id}")
    public Deal getDeal(@RequestHeader("X-Tenant-ID") UUID tenantId,
                        @PathVariable("id") UUID dealId) {
        return dealService.getDealById(tenantId, dealId)
                .orElseThrow(() -> new RuntimeException("Deal not found"));
    }

    // Create a new deal (requires tenant + user)
    @PostMapping
    public Deal createDeal(@RequestHeader("X-Tenant-ID") UUID tenantId,
                           @RequestHeader("X-User-ID") UUID userId,
                           @RequestBody Deal deal) {
        return dealService.createDeal(tenantId, userId, deal);
    }

    // Update stage + amount + add note + log stage transition
    @PatchMapping("/{id}")
    public Deal updateStage(@RequestHeader("X-Tenant-ID") UUID tenantId,
                            @RequestHeader("X-User-ID") UUID userId,
                            @PathVariable("id") UUID dealId,
                            @RequestBody StageUpdateRequest request) {
        return dealService.updateStage(
                tenantId,
                dealId,
                userId,
                request.getNewStage(),
                request.getNotes(),
                request.getAmount()
        );
    }

    // Group deals by stage for Kanban
    @GetMapping("/by-stage")
    public Map<String, List<DealSummaryDto>> getDealsByStage(@RequestHeader("X-Tenant-ID") UUID tenantId) {
        return dealService.getDealsGroupedByStage(tenantId);
    }

    // --- NEW ENDPOINTS ---

    // Add a borrower to a deal
    @PostMapping("/{id}/borrower")
    public Borrower addBorrower(@RequestHeader("X-Tenant-ID") UUID tenantId,
                                @RequestHeader("X-User-ID") UUID userId,
                                @PathVariable("id") UUID dealId,
                                @RequestParam("partyId") UUID partyId,
                                @RequestBody Borrower borrower) {
        return dealService.addBorrowerToDeal(tenantId, userId, dealId, partyId, borrower);
    }


    // Add a broker to a deal
    @PostMapping("/{id}/broker")
    public Broker addBroker(@RequestHeader("X-Tenant-ID") UUID tenantId,
                            @RequestHeader("X-User-ID") UUID userId,
                            @PathVariable("id") UUID dealId,
                            @RequestBody Broker broker) {
        return dealService.addBrokerToDeal(tenantId, userId, dealId, broker);
    }

    // Add a guarantor to a deal
    @PostMapping("/{id}/guarantor")
    public Guarantor addGuarantor(@RequestHeader("X-Tenant-ID") UUID tenantId,
                                  @RequestHeader("X-User-ID") UUID userId,
                                  @PathVariable("id") UUID dealId,
                                  @RequestBody Guarantor guarantor) {
        return dealService.addGuarantorToDeal(tenantId, userId, dealId, guarantor);
    }

    // DTO for stage update
    public static class StageUpdateRequest {
        private String newStage;
        private String notes;
        private String amount;

        public String getNewStage() { return newStage; }
        public void setNewStage(String newStage) { this.newStage = newStage; }

        public String getNotes() { return notes; }
        public void setNotes(String notes) { this.notes = notes; }

        public String getAmount() { return amount; }
        public void setAmount(String amount) { this.amount = amount; }
    }
}
