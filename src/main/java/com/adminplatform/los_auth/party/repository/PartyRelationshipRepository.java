package com.adminplatform.los_auth.party.repository;

import com.adminplatform.los_auth.party.entity.PartyRelationship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PartyRelationshipRepository extends JpaRepository<PartyRelationship, UUID> {
    List<PartyRelationship> findByTenantIdAndSrcPartyId(UUID tenantId, UUID srcPartyId);
}
