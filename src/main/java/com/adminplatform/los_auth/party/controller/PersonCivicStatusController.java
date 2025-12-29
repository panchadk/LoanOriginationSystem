package com.adminplatform.los_auth.party.controller;

import com.adminplatform.los_auth.party.entity.PersonCivicStatus;
import com.adminplatform.los_auth.party.service.PersonCivicStatusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/party/{partyId}/civic-status")
public class PersonCivicStatusController {

    private final PersonCivicStatusService service;

    public PersonCivicStatusController(PersonCivicStatusService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PersonCivicStatus>> getStatuses(
            @RequestHeader("X-Tenant-ID") UUID tenantId,
            @PathVariable UUID partyId
    ) {
        return ResponseEntity.ok(service.getStatuses(tenantId, partyId));
    }

    @PostMapping
    public ResponseEntity<PersonCivicStatus> createStatus(
            @RequestHeader("X-Tenant-ID") UUID tenantId,
            @PathVariable UUID partyId,
            @RequestBody PersonCivicStatus status
    ) {
        status.setTenantId(tenantId);
        status.setPartyId(partyId);
        return ResponseEntity.ok(service.saveStatus(status));
    }

    @PutMapping("/{statusId}")
    public ResponseEntity<PersonCivicStatus> updateStatus(
            @RequestHeader("X-Tenant-ID") UUID tenantId,
            @PathVariable UUID partyId,
            @PathVariable UUID statusId,
            @RequestBody PersonCivicStatus status
    ) {
        status.setTenantId(tenantId);
        status.setPartyId(partyId);
        status.setCivicStatusId(statusId);
        return ResponseEntity.ok(service.saveStatus(status));
    }

    @DeleteMapping("/{statusId}")
    public ResponseEntity<Void> deleteStatus(
            @RequestHeader("X-Tenant-ID") UUID tenantId,
            @PathVariable UUID statusId
    ) {
        service.deleteStatus(statusId);
        return ResponseEntity.noContent().build();
    }
}
