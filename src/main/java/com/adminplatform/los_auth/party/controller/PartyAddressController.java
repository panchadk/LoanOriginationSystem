package com.adminplatform.los_auth.party.controller;

import com.adminplatform.los_auth.party.dto.PartyAddressDto;
import com.adminplatform.los_auth.party.entity.PartyAddressId;
import com.adminplatform.los_auth.party.service.PartyAddressService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/party/{partyId}/address")
@CrossOrigin(origins = "*", allowedHeaders = "*") // ✅ Allow frontend access
public class PartyAddressController {

    private static final Logger log = LoggerFactory.getLogger(PartyAddressController.class);
    private final PartyAddressService service;

    public PartyAddressController(PartyAddressService service) {
        this.service = service;
    }

    // ✅ GET all addresses for a party
    @GetMapping
    public ResponseEntity<List<PartyAddressDto>> getAddresses(
            @RequestHeader("X-Tenant-ID") UUID tenantId,
            @PathVariable UUID partyId
    ) {
        return ResponseEntity.ok(service.getAddresses(tenantId, partyId));
    }

    // ✅ POST new address link
    @PostMapping
    public ResponseEntity<PartyAddressDto> createAddress(
            @RequestHeader("X-Tenant-ID") UUID tenantId,
            @PathVariable UUID partyId,
            @Valid @RequestBody PartyAddressDto dto
    ) {
        log.debug("🔍 Inside PartyAddressController#createAddress");
        PartyAddressDto created = service.addAddress(tenantId, partyId, dto);
        return ResponseEntity.ok(created);
    }

    // ✅ PUT update address link
    @PutMapping("/{addressId}/{effectiveFrom}")
    public ResponseEntity<PartyAddressDto> updateAddress(
            @RequestHeader("X-Tenant-ID") UUID tenantId,
            @PathVariable UUID partyId,
            @PathVariable UUID addressId,
            @PathVariable LocalDate effectiveFrom,
            @Valid @RequestBody PartyAddressDto dto
    ) {
        if (!dto.addressId().equals(addressId) || !dto.effectiveFrom().equals(effectiveFrom)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Address ID or effectiveFrom mismatch");
        }

        PartyAddressDto updated = service.updateAddress(tenantId, partyId, dto);
        return ResponseEntity.ok(updated);
    }

    // ✅ DELETE address link
    @DeleteMapping("/{addressId}/{effectiveFrom}")
    public ResponseEntity<Void> deleteAddress(
            @RequestHeader("X-Tenant-ID") UUID tenantId,
            @PathVariable UUID partyId,
            @PathVariable UUID addressId,
            @PathVariable LocalDate effectiveFrom
    ) {
        log.debug("Received DELETE request with tenantId: {}, partyId: {}, addressId: {}, effectiveFrom: {}",
                tenantId, partyId, addressId, effectiveFrom);

        PartyAddressId id = new PartyAddressId(tenantId, partyId, addressId, effectiveFrom);
        log.debug("Constructed composite key: {}", id);
        service.delete(id);
        log.info("Successfully deleted address with ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}
