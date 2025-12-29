package com.adminplatform.los_auth.deal.repository;

import com.adminplatform.los_auth.deal.entity.DealParty;
import com.adminplatform.los_auth.deal.entity.DealPartyId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DealPartyRepository extends JpaRepository<DealParty, DealPartyId> {

    // Correct method name: navigate into the embedded ID
    List<DealParty> findByIdTenantIdAndIdDealId(UUID tenantId, UUID dealId);
}
