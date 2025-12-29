package com.adminplatform.los_auth.deal.service;
import com.adminplatform.los_auth.deal.dto.DealSummaryDto;
import com.adminplatform.los_auth.deal.entity.Deal;
import com.adminplatform.los_auth.deal.repository.DealNoteRepository;
import com.adminplatform.los_auth.deal.repository.DealRepository;
import com.adminplatform.los_auth.deal.repository.DealStageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DealServiceTest {

    private DealRepository dealRepository;
    private DealNoteRepository dealNoteRepository;
    private DealStageRepository dealStageRepository;
    private DealService dealService;

    @BeforeEach
    void setUp() {
        dealRepository = mock(DealRepository.class);
        dealNoteRepository = mock(DealNoteRepository.class);   // mock instead of null
        dealStageRepository = mock(DealStageRepository.class); // mock instead of null

        dealService = new DealService(dealRepository, dealNoteRepository, dealStageRepository);
    }

    @Test
    void testGetDealsGroupedByStage() {
        UUID tenantId = UUID.randomUUID();

        Deal deal1 = new Deal();
        deal1.setTenantId(tenantId);
        deal1.setDealId(UUID.randomUUID());
        deal1.setStage("PREQUAL");
        deal1.setStageVersion(1);
        deal1.setAmountRequested(new BigDecimal("100000"));
        deal1.setProductCode("COMMERCIAL");

        Deal deal2 = new Deal();
        deal2.setTenantId(tenantId);
        deal2.setDealId(UUID.randomUUID());
        deal2.setStage("UNDER_REVIEW");
        deal2.setStageVersion(2);
        deal2.setAmountRequested(new BigDecimal("200000"));
        deal2.setProductCode("RESIDENTIAL");

        Deal deal3 = new Deal();
        deal3.setTenantId(tenantId);
        deal3.setDealId(UUID.randomUUID());
        deal3.setStage("PREQUAL");
        deal3.setStageVersion(1);
        deal3.setAmountRequested(new BigDecimal("150000"));
        deal3.setProductCode("COMMERCIAL");

        when(dealRepository.findByTenantId(tenantId)).thenReturn(Arrays.asList(deal1, deal2, deal3));

        Map<String, List<DealSummaryDto>> grouped = dealService.getDealsGroupedByStage(tenantId);

        // Assertions
        assertEquals(2, grouped.get("PREQUAL").size(), "PREQUAL should have 2 deals");
        assertEquals(1, grouped.get("UNDER_REVIEW").size(), "UNDER_REVIEW should have 1 deal");

        // Check contents
        List<UUID> prequalIds = grouped.get("PREQUAL").stream()
                .map(DealSummaryDto::getDealId)
                .collect(Collectors.toList());

        assertTrue(prequalIds.contains(deal1.getDealId()));
        assertTrue(prequalIds.contains(deal3.getDealId()));
    }
}
