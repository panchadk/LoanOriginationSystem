package com.adminplatform.los_auth.outbox.service;

import com.adminplatform.los_auth.outbox.entity.OutboxEvent;
import com.adminplatform.los_auth.outbox.repository.OutboxEventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OutboxService {

    private final OutboxEventRepository repo;

    public OutboxService(OutboxEventRepository repo) {
        this.repo = repo;
    }

    public List<OutboxEvent> getEventsForParty(UUID tenantId, UUID partyId) {
        return repo.findByTenantIdAndAggregateTypeAndAggregateId(
                tenantId, "PARTY", partyId
        );
    }
}
