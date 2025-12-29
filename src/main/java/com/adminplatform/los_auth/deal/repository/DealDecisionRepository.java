package com.adminplatform.los_auth.deal.repository;

import com.adminplatform.los_auth.deal.entity.DealDecision;
import com.adminplatform.los_auth.deal.entity.DealDecisionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DealDecisionRepository extends JpaRepository<DealDecision, DealDecisionId> {
    List<DealDecision> findByTenantIdAndDealId(UUID tenantId, UUID dealId);
}
