package com.adminplatform.los_auth.deal.service;

import com.adminplatform.los_auth.deal.dto.DealSummaryDto;
import com.adminplatform.los_auth.deal.entity.DealSummary;
import com.adminplatform.los_auth.deal.entity.DealSummaryId;
import com.adminplatform.los_auth.deal.repository.DealSummaryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DealSummaryService {

    private final DealSummaryRepository dealSummaryRepository;

    public DealSummaryService(DealSummaryRepository dealSummaryRepository) {
        this.dealSummaryRepository = dealSummaryRepository;
    }

    public List<DealSummary> getSummaries(UUID tenantId) {
        return dealSummaryRepository.findByTenantId(tenantId);
    }

    public Optional<DealSummary> getSummary(UUID tenantId, UUID dealId) {
        return dealSummaryRepository.findById(new DealSummaryId(tenantId, dealId));
    }

    public List<DealSummary> getSummariesByStage(UUID tenantId, String stage) {
        return dealSummaryRepository.findByTenantIdAndStage(tenantId, stage);
    }

    // Group summaries by stage for Kanban
    public Map<String, List<DealSummaryDto>> getDealsGroupedByStage(UUID tenantId) {
        List<DealSummary> summaries = dealSummaryRepository.findByTenantId(tenantId);

        return summaries.stream()
                .collect(Collectors.groupingBy(
                        DealSummary::getStage,
                        Collectors.mapping(
                                s -> new DealSummaryDto(
                                        s.getDealId(),
                                        s.getAmountRequested(),
                                        s.getStageVersion(),
                                        s.getLastDecision()
                                ),
                                Collectors.toList()
                        )
                ));
    }
}
