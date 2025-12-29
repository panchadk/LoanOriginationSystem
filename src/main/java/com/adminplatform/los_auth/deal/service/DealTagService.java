package com.adminplatform.los_auth.deal.service;

import com.adminplatform.los_auth.deal.entity.DealTag;
import com.adminplatform.los_auth.deal.repository.DealTagRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DealTagService {

    private final DealTagRepository dealTagRepository;

    public DealTagService(DealTagRepository dealTagRepository) {
        this.dealTagRepository = dealTagRepository;
    }

    public DealTag addTag(UUID tenantId, UUID dealId, String tag, UUID createdBy) {
        DealTag dealTag = new DealTag();
        dealTag.setTenantId(tenantId);
        dealTag.setDealId(dealId);
        dealTag.setTag(tag);
        dealTag.setCreatedBy(createdBy);
        dealTag.setCreatedAt(LocalDateTime.now());
        return dealTagRepository.save(dealTag);
    }

    public List<DealTag> getTags(UUID tenantId, UUID dealId) {
        return dealTagRepository.findByTenantIdAndDealId(tenantId, dealId);
    }
}
