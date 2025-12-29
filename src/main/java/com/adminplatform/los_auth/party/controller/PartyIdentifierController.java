package com.adminplatform.los_auth.party.controller;

import com.adminplatform.los_auth.party.entity.PartyIdentifier;
import com.adminplatform.los_auth.party.service.PartyIdentifierService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/party/{partyId}/identifier")
public class PartyIdentifierController {

    private final PartyIdentifierService service;

    public PartyIdentifierController(PartyIdentifierService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PartyIdentifier>> getIdentifiers(
            @RequestHeader("X-Tenant-ID") UUID tenantId,
            @PathVariable UUID partyId
    ) {
        return ResponseEntity.ok(service.getIdentifiers(tenantId, partyId));
    }

    @PostMapping
    public ResponseEntity<PartyIdentifier> createIdentifier(
            @RequestHeader("X-Tenant-ID") UUID tenantId,
            @PathVariable UUID partyId,
            @RequestBody PartyIdentifier identifier
    ) {
        identifier.setTenantId(tenantId);
        identifier.setPartyId(partyId);
        identifier.setIdentifierId(UUID.randomUUID());
        return ResponseEntity.ok(service.saveIdentifier(identifier));
    }

    @PutMapping("/{identifierId}")
    public ResponseEntity<PartyIdentifier> updateIdentifier(
            @RequestHeader("X-Tenant-ID") UUID tenantId,
            @PathVariable UUID partyId,
            @PathVariable UUID identifierId,
            @RequestBody PartyIdentifier identifier
    ) {
        identifier.setTenantId(tenantId);
        identifier.setPartyId(partyId);
        identifier.setIdentifierId(identifierId);
        return ResponseEntity.ok(service.saveIdentifier(identifier));
    }

    @DeleteMapping("/{identifierId}")
    public ResponseEntity<Void> deleteIdentifier(
            @RequestHeader("X-Tenant-ID") UUID tenantId,
            @PathVariable UUID identifierId
    ) {
        service.deleteIdentifier(identifierId);
        return ResponseEntity.noContent().build();
    }
}
