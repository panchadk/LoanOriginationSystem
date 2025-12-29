package com.adminplatform.los_auth.deal.service;

import com.adminplatform.los_auth.deal.entity.DealDecision;
import com.adminplatform.los_auth.deal.entity.DealSummaryId;
import com.adminplatform.los_auth.deal.repository.DealDecisionRepository;
import com.adminplatform.los_auth.deal.repository.DealSummaryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DealDecisionService {

    private final DealDecisionRepository dealDecisionRepository;
    private final DealSummaryRepository dealSummaryRepository;

    public DealDecisionService(DealDecisionRepository dealDecisionRepository,
                               DealSummaryRepository dealSummaryRepository) {
        this.dealDecisionRepository = dealDecisionRepository;
        this.dealSummaryRepository = dealSummaryRepository;
    }

    public DealDecision recordDecision(UUID tenantId,
                                       UUID dealId,
                                       String type,
                                       String reasonCode,
                                       String notes,
                                       UUID decidedBy) {
        // Create and save the decision
        DealDecision decision = new DealDecision();
        decision.setTenantId(tenantId);
        decision.setDecisionId(UUID.randomUUID());
        decision.setDealId(dealId);
        decision.setType(type);
        decision.setReasonCode(reasonCode);
        decision.setNotes(notes);
        decision.setDecidedBy(decidedBy);
        decision.setDecidedAt(LocalDateTime.now());

        DealDecision saved = dealDecisionRepository.save(decision);

        // Update summary row
        updateSummaryDecision(tenantId, dealId, type);

        return saved;
    }

    public List<DealDecision> getDecisions(UUID tenantId, UUID dealId) {
        return dealDecisionRepository.findByTenantIdAndDealId(tenantId, dealId);
    }

    private void updateSummaryDecision(UUID tenantId, UUID dealId, String decisionType) {
        dealSummaryRepository.findById(new DealSummaryId(tenantId, dealId)).ifPresent(summary -> {
            summary.setLastDecision(decisionType);
            summary.setLastUpdatedAt(LocalDateTime.now());
            dealSummaryRepository.save(summary);
        });
    }
}
