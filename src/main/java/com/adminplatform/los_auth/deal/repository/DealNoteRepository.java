package com.adminplatform.los_auth.deal.repository;

import com.adminplatform.los_auth.deal.entity.DealNote;
import com.adminplatform.los_auth.deal.entity.DealNoteId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DealNoteRepository extends JpaRepository<DealNote, DealNoteId> {
    List<DealNote> findByTenantIdAndDealId(UUID tenantId, UUID dealId);
}
