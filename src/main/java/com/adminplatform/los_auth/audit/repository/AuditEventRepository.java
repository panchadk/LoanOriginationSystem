package com.adminplatform.los_auth.audit.repository;

import com.adminplatform.los_auth.audit.entity.AuditEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AuditEventRepository extends JpaRepository<AuditEvent, UUID> {

    List<AuditEvent> findByTenantIdAndEntityIdOrderByOccurredAtDesc(UUID tenantId, UUID entityId);
}
