package com.adminplatform.los_auth.deal.repository;

import com.adminplatform.los_auth.deal.entity.DealAssignment;
import com.adminplatform.los_auth.deal.entity.DealAssignmentId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DealAssignmentRepository extends JpaRepository<DealAssignment, DealAssignmentId> {
    List<DealAssignment> findByTenantIdAndDealIdAndEndedAtIsNull(UUID tenantId, UUID dealId);
}
