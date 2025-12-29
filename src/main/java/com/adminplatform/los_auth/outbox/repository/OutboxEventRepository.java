package com.adminplatform.los_auth.outbox.repository;

import com.adminplatform.los_auth.outbox.entity.OutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OutboxEventRepository extends JpaRepository<OutboxEvent, UUID> {

    List<OutboxEvent> findByTenantIdAndAggregateTypeAndAggregateId(
            UUID tenantId, String aggregateType, UUID aggregateId
    );
}
