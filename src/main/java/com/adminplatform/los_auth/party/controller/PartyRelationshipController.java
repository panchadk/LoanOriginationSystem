package com.adminplatform.los_auth.party.controller;

import com.adminplatform.los_auth.party.entity.PartyRelationship;
import com.adminplatform.los_auth.party.service.PartyRelationshipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/party/{partyId}/relationship")
public class PartyRelationshipController {

    private final PartyRelationshipService service;

    public PartyRelationshipController(PartyRelationshipService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PartyRelationship>> getRelationships(
            @RequestHeader("X-Tenant-ID") UUID tenantId,
            @PathVariable UUID partyId
    ) {
        return ResponseEntity.ok(service.getRelationships(tenantId, partyId));
    }

    @PostMapping
    public ResponseEntity<PartyRelationship> createRelationship(
            @RequestHeader("X-Tenant-ID") UUID tenantId,
            @PathVariable UUID partyId,
            @RequestBody PartyRelationship relationship
    ) {
        relationship.setTenantId(tenantId);
        relationship.setSrcPartyId(partyId);
        relationship.setRelationshipId(UUID.randomUUID());
        return ResponseEntity.ok(service.saveRelationship(relationship));
    }

    @PutMapping("/{relationshipId}")
    public ResponseEntity<PartyRelationship> updateRelationship(
            @RequestHeader("X-Tenant-ID") UUID tenantId,
            @PathVariable UUID partyId,
            @PathVariable UUID relationshipId,
            @RequestBody PartyRelationship relationship
    ) {
        relationship.setTenantId(tenantId);
        relationship.setSrcPartyId(partyId);
        relationship.setRelationshipId(relationshipId);
        return ResponseEntity.ok(service.saveRelationship(relationship));
    }

    @DeleteMapping("/{relationshipId}")
    public ResponseEntity<Void> deleteRelationship(
            @RequestHeader("X-Tenant-ID") UUID tenantId,
            @PathVariable UUID relationshipId
    ) {
        service.deleteRelationship(relationshipId);
        return ResponseEntity.noContent().build();
    }
}
