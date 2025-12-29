package com.adminplatform.los_auth.deal.repository;

import com.adminplatform.los_auth.deal.entity.Deal;
import com.adminplatform.los_auth.deal.entity.DealId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DealRepository extends JpaRepository<Deal, DealId> {

    List<Deal> findByTenantId(UUID tenantId);

    List<Deal> findByTenantIdAndStage(UUID tenantId, String stage);

    List<Deal> findByTenantIdAndAssignedToUserId(UUID tenantId, UUID userId);
}
