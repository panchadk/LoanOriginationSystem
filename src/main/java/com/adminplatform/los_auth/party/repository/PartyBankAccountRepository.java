package com.adminplatform.los_auth.party.repository;

import com.adminplatform.los_auth.party.entity.PartyBankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PartyBankAccountRepository extends JpaRepository<PartyBankAccount, UUID> {
    List<PartyBankAccount> findByTenantIdAndPartyId(UUID tenantId, UUID partyId);
}
