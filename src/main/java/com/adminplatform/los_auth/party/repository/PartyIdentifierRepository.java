package com.adminplatform.los_auth.party.repository;

import com.adminplatform.los_auth.party.entity.PartyIdentifier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PartyIdentifierRepository extends JpaRepository<PartyIdentifier, UUID> {
    List<PartyIdentifier> findByTenantIdAndPartyId(UUID tenantId, UUID partyId);
}
