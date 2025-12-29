package com.adminplatform.los_auth.deal.repository;

import com.adminplatform.los_auth.deal.entity.DealSummary;
import com.adminplatform.los_auth.deal.entity.DealSummaryId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DealSummaryRepository extends JpaRepository<DealSummary, DealSummaryId> {
    List<DealSummary> findByTenantId(UUID tenantId);
    List<DealSummary> findByTenantIdAndStage(UUID tenantId, String stage);
}
