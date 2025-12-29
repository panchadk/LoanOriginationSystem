package com.adminplatform.los_auth.party.repository;

import com.adminplatform.los_auth.party.entity.PersonCivicStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PersonCivicStatusRepository extends JpaRepository<PersonCivicStatus, UUID> {
    List<PersonCivicStatus> findByTenantIdAndPartyId(UUID tenantId, UUID partyId);
}
