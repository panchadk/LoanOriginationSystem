package com.adminplatform.los_auth.party.service;
import com.adminplatform.los_auth.party.entity.PartyBankAccount;
import com.adminplatform.los_auth.party.repository.PartyBankAccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PartyBankAccountService {

    private final PartyBankAccountRepository repository;

    public PartyBankAccountService(PartyBankAccountRepository repository) {
        this.repository = repository;
    }

    public List<PartyBankAccount> getAccounts(UUID tenantId, UUID partyId) {
        return repository.findByTenantIdAndPartyId(tenantId, partyId);
    }

    public PartyBankAccount saveAccount(PartyBankAccount account) {
        return repository.save(account);
    }

    public void deleteAccount(UUID accountId) {
        repository.deleteById(accountId);
    }
}
