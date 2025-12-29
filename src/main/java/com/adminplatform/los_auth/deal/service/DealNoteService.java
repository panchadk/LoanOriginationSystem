package com.adminplatform.los_auth.deal.service;

import com.adminplatform.los_auth.deal.entity.DealNote;
import com.adminplatform.los_auth.deal.repository.DealNoteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DealNoteService {

    private final DealNoteRepository dealNoteRepository;

    public DealNoteService(DealNoteRepository dealNoteRepository) {
        this.dealNoteRepository = dealNoteRepository;
    }

    public DealNote addNote(UUID tenantId, UUID dealId, UUID authorUserId, String visibility, String body) {
        DealNote note = new DealNote();
        note.setTenantId(tenantId);
        note.setNoteId(UUID.randomUUID());
        note.setDealId(dealId);
        note.setAuthorUserId(authorUserId);
        note.setVisibility(visibility);
        note.setBody(body);
        note.setCreatedAt(LocalDateTime.now());
        return dealNoteRepository.save(note);
    }

    public List<DealNote> getNotes(UUID tenantId, UUID dealId) {
        return dealNoteRepository.findByTenantIdAndDealId(tenantId, dealId);
    }
}
