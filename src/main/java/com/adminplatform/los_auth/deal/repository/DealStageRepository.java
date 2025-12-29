package com.adminplatform.los_auth.deal.repository;

import com.adminplatform.los_auth.deal.entity.DealId;
import com.adminplatform.los_auth.deal.entity.DealStage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DealStageRepository extends JpaRepository<DealStage, UUID> {
    List<DealStage> findByDealId(DealId dealId);
}
