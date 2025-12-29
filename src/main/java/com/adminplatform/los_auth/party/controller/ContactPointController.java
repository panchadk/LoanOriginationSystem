package com.adminplatform.los_auth.party.controller;

import com.adminplatform.los_auth.party.dto.ContactPointDto;
import com.adminplatform.los_auth.party.service.ContactPointService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/contact-point")
public class ContactPointController {

    private final ContactPointService contactPointService;

    public ContactPointController(ContactPointService contactPointService) {
        this.contactPointService = contactPointService;
    }

    @PostMapping
    public ResponseEntity<ContactPointDto> createContactPoint(
            @RequestHeader("X-Tenant-ID") UUID tenantId,
            @RequestBody ContactPointDto dto
    ) {
        ContactPointDto saved = contactPointService.create(tenantId, dto);
        return ResponseEntity.ok(saved);
    }
}
