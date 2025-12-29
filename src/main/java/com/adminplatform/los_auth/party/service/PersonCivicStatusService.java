package com.adminplatform.los_auth.party.service;

import com.adminplatform.los_auth.party.entity.PersonCivicStatus;
import com.adminplatform.los_auth.party.repository.PersonCivicStatusRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PersonCivicStatusService {

    private final PersonCivicStatusRepository repository;

    public PersonCivicStatusService(PersonCivicStatusRepository repository) {
        this.repository = repository;
    }

    public List<PersonCivicStatus> getStatuses(UUID tenantId, UUID partyId) {
        return repository.findByTenantIdAndPartyId(tenantId, partyId);
    }

    public PersonCivicStatus saveStatus(PersonCivicStatus status) {
        return repository.save(status);
    }

    public void deleteStatus(UUID statusId) {
        repository.deleteById(statusId);
    }
}
