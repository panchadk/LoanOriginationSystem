package com.adminplatform.los_auth.deal.controller;

import com.adminplatform.los_auth.deal.entity.DealNote;
import com.adminplatform.los_auth.deal.service.DealNoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/deal/{dealId}/notes")
public class DealNoteController {

    private final DealNoteService dealNoteService;

    public DealNoteController(DealNoteService dealNoteService) {
        this.dealNoteService = dealNoteService;
    }

    @PostMapping
    public ResponseEntity<DealNote> addNote(
            @RequestHeader("X-Tenant-ID") UUID tenantId,
            @PathVariable UUID dealId,
            @RequestHeader("X-User-ID") UUID authorUserId,
            @RequestParam String visibility,
            @RequestBody String body
    ) {
        return ResponseEntity.ok(
                dealNoteService.addNote(tenantId, dealId, authorUserId, visibility, body)
        );
    }

    @GetMapping
    public ResponseEntity<List<DealNote>> getNotes(
            @RequestHeader("X-Tenant-ID") UUID tenantId,
            @PathVariable UUID dealId
    ) {
        return ResponseEntity.ok(dealNoteService.getNotes(tenantId, dealId));
    }
}
