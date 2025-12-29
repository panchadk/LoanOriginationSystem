package com.adminplatform.los_auth.party.controller;
import com.adminplatform.los_auth.audit.entity.AuditEvent;
import com.adminplatform.los_auth.party.service.AuditEventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/party/{partyId}/audit")
public class AuditEventController {

    private final AuditEventService service;

    public AuditEventController(AuditEventService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<AuditEvent>> getAuditEvents(
            @RequestHeader("X-Tenant-ID") UUID tenantId,
            @PathVariable UUID partyId
    ) {
        return ResponseEntity.ok(service.getPartyAudit(tenantId, partyId));
    }
}
