package com.adminplatform.los_auth.deal.service;

import com.adminplatform.los_auth.deal.dto.DealPartyRequestDto;
import com.adminplatform.los_auth.deal.entity.DealParty;
import com.adminplatform.los_auth.deal.repository.DealPartyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DealPartyService {

    private final DealPartyRepository dealPartyRepository;

    public DealPartyService(DealPartyRepository dealPartyRepository) {
        this.dealPartyRepository = dealPartyRepository;
    }

    public void assignParty(UUID tenantId, UUID dealId, DealPartyRequestDto dto) {
        DealParty dp = new DealParty();
        dp.setTenantId(tenantId);
        dp.setDealId(dealId);
        dp.setPartyId(dto.partyId);
        dp.setRoleCode(dto.roleCode);
        dp.setLiabilityPct(dto.liabilityPct);
        dp.setGuaranteeType(dto.guaranteeType);
        dp.setGuaranteeCapAmount(dto.guaranteeCapAmount);
        dp.setEffectiveFrom(dto.effectiveFrom);
        dp.setEffectiveTo(dto.effectiveTo);
        dealPartyRepository.save(dp);
    }

    public List<DealParty> getPartiesForDeal(UUID tenantId, UUID dealId) {
        return dealPartyRepository.findByIdTenantIdAndIdDealId(tenantId, dealId);
    }
}
