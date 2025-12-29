package com.adminplatform.los_auth.party.service;

import com.adminplatform.los_auth.party.entity.PartyRelationship;
import com.adminplatform.los_auth.party.repository.PartyRelationshipRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PartyRelationshipService {

    private final PartyRelationshipRepository repository;

    public PartyRelationshipService(PartyRelationshipRepository repository) {
        this.repository = repository;
    }

    public List<PartyRelationship> getRelationships(UUID tenantId, UUID srcPartyId) {
        return repository.findByTenantIdAndSrcPartyId(tenantId, srcPartyId);
    }

    public PartyRelationship saveRelationship(PartyRelationship relationship) {
        return repository.save(relationship);
    }

    public void deleteRelationship(UUID relationshipId) {
        repository.deleteById(relationshipId);
    }
}
