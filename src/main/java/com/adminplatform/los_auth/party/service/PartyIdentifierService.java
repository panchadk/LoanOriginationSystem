package com.adminplatform.los_auth.party.service;

import com.adminplatform.los_auth.party.entity.PartyIdentifier;
import com.adminplatform.los_auth.party.repository.PartyIdentifierRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PartyIdentifierService {

    private final PartyIdentifierRepository repository;

    public PartyIdentifierService(PartyIdentifierRepository repository) {
        this.repository = repository;
    }

    public List<PartyIdentifier> getIdentifiers(UUID tenantId, UUID partyId) {
        return repository.findByTenantIdAndPartyId(tenantId, partyId);
    }

    public PartyIdentifier saveIdentifier(PartyIdentifier identifier) {
        return repository.save(identifier);
    }

    public void deleteIdentifier(UUID identifierId) {
        repository.deleteById(identifierId);
    }
}
