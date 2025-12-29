package com.adminplatform.los_auth.deal.repository;

import com.adminplatform.los_auth.deal.entity.DealTag;
import com.adminplatform.los_auth.deal.entity.DealTagId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DealTagRepository extends JpaRepository<DealTag, DealTagId> {
    List<DealTag> findByTenantIdAndDealId(UUID tenantId, UUID dealId);
}
