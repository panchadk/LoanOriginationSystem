package com.adminplatform.los_auth.party.repository;

import com.adminplatform.los_auth.party.entity.PartyAddress;
import java.util.List;

import com.adminplatform.los_auth.party.entity.PartyAddressId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PartyAddressRepository extends JpaRepository<PartyAddress, PartyAddressId> {
    List<PartyAddress> findByIdTenantIdAndIdPartyId(UUID tenantId, UUID partyId);
}
