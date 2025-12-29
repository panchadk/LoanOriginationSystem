package com.adminplatform.los_auth.party.controller;

import com.adminplatform.los_auth.audit.entity.AuditEvent;
import com.adminplatform.los_auth.party.dto.*;
import com.adminplatform.los_auth.party.service.PartyAddressService;
import com.adminplatform.los_auth.party.service.PartyContactService;
import com.adminplatform.los_auth.party.service.PartyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/party")
public class PartyController {

    private final PartyService partyService;
    private final PartyContactService partyContactService;
    private final PartyAddressService partyAddressService;

    public PartyController(
            PartyService partyService,
            PartyContactService partyContactService,
            PartyAddressService partyAddressService
    ) {
        System.out.println("✅ PartyController initialized");
        this.partyService = partyService;
        this.partyContactService = partyContactService;
        this.partyAddressService = partyAddressService;
    }

    @GetMapping
    public ResponseEntity<List<PartySummaryDto>> searchParties(
            @RequestHeader("X-Tenant-ID") UUID tenantId,
            @RequestParam(name = "search", defaultValue = "") String search
    ) {
        return ResponseEntity.ok(partyService.searchParties(tenantId, search));
    }

    @PostMapping
    public ResponseEntity<UUID> createParty(
            @RequestHeader("X-Tenant-ID") UUID tenantId,
            @Valid @RequestBody PartyCreateRequestDto request
    ) {
        try {
            UUID partyId = partyService.createParty(tenantId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(partyId);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create party", e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateParty(
            @RequestHeader("X-Tenant-ID") UUID tenantId,
            @PathVariable UUID id,
            @Valid @RequestBody PartyCreateRequestDto request
    ) {
        partyService.updateParty(tenantId, id, request);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> toggleStatus(
            @RequestHeader("X-Tenant-ID") UUID tenantId,
            @PathVariable UUID id,
            @RequestParam String status
    ) {
        partyService.updateStatus(tenantId, id, status);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartyDetailDto> getPartyDetails(
            @RequestHeader("X-Tenant-ID") UUID tenantId,
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(partyService.getPartyDetails(tenantId, id));
    }

    @GetMapping("/{id}/audit-trail")
    public ResponseEntity<List<AuditEvent>> getAuditTrail(
            @RequestHeader("X-Tenant-ID") UUID tenantId,
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(partyService.getAuditTrail(tenantId, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParty(
            @RequestHeader("X-Tenant-ID") UUID tenantId,
            @PathVariable UUID id
    ) {
        partyService.deleteParty(tenantId, id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{partyId}/contact/{contactId}")
    public ResponseEntity<PartyContactDto> updateContact(
            @RequestHeader("X-Tenant-ID") UUID tenantId,
            @PathVariable UUID partyId,
            @PathVariable UUID contactId,
            @RequestBody PartyContactDto dto
    ) {
        if (!dto.contactId().equals(contactId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Contact ID mismatch");
        }
        return ResponseEntity.ok(partyContactService.updateContact(tenantId, partyId, dto));
    }

    @PostMapping("/ping")
    public ResponseEntity<String> ping(@RequestBody String body) {
        return ResponseEntity.ok("pong");
    }
}
