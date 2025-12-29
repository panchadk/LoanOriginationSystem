package com.adminplatform.los_auth.party.controller;

import com.adminplatform.los_auth.common.dto.ApiError;
import com.adminplatform.los_auth.party.dto.PartyContactDto;
import com.adminplatform.los_auth.party.service.PartyContactService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/party/{partyId}/contact")
public class PartyContactController {

    private final PartyContactService service;

    public PartyContactController(PartyContactService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PartyContactDto>> getContacts(
            @RequestHeader("X-Tenant-ID") UUID tenantId,
            @PathVariable UUID partyId
    ) {
        return ResponseEntity.ok(service.getContacts(tenantId, partyId));
    }
    @PostMapping
    public ResponseEntity<?> addContact(
            @RequestHeader("X-Tenant-ID") UUID tenantId,
            @PathVariable UUID partyId,
            @RequestBody PartyContactDto contactDto
    ) {
        try {
            PartyContactDto saved = service.addContact(tenantId, partyId, contactDto);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ApiError("Failed to add contact"));
        }
    }
    @DeleteMapping("/{contactId}/{effectiveFrom}")
    public ResponseEntity<?> deleteContact(
            @RequestHeader("X-Tenant-ID") UUID tenantId,
            @PathVariable UUID partyId,
            @PathVariable UUID contactId,
            @PathVariable String effectiveFrom
    ) {
        try {
            service.deleteContact(tenantId, partyId, contactId, LocalDate.parse(effectiveFrom));
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ApiError("Failed to delete contact"));
        }
    }

}
