package com.adminplatform.los_auth.party.controller;

import com.adminplatform.los_auth.outbox.entity.OutboxEvent;
import com.adminplatform.los_auth.outbox.service.OutboxService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.adminplatform.los_auth.common.dto.ApiError;


import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/party/{partyId}/outbox")
public class PartyOutboxController {

    private final OutboxService outboxService;

    public PartyOutboxController(OutboxService outboxService) {
        this.outboxService = outboxService;
    }

    @GetMapping
    public ResponseEntity<?> getOutboxEvents(
            @RequestHeader(value = "X-Tenant-ID", required = false) String tenantIdStr,
            @PathVariable UUID partyId
    ) {
        try {
            if (tenantIdStr == null || tenantIdStr.isBlank()) {
                return ResponseEntity
                        .badRequest()
                        .body(new ApiError("Missing or empty X-Tenant-ID header"));
            }

            UUID tenantId = UUID.fromString(tenantIdStr);
            List<OutboxEvent> events = outboxService.getEventsForParty(tenantId, partyId);
            return ResponseEntity.ok(events);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiError("Invalid tenant ID format"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(500)
                    .body(new ApiError("Internal server error"));
        }
    }


}
