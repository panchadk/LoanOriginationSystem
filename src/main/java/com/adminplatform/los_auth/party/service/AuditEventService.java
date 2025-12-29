package com.adminplatform.los_auth.party.service;
import com.adminplatform.los_auth.audit.entity.AuditEvent;
import com.adminplatform.los_auth.audit.repository.AuditEventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AuditEventService {

    private final AuditEventRepository repository;

    public AuditEventService(AuditEventRepository repository) {
        this.repository = repository;
    }

    public List<AuditEvent> getPartyAudit(UUID tenantId, UUID partyId) {
        return repository.findByTenantIdAndEntityIdOrderByOccurredAtDesc(tenantId, partyId);
    }
}
