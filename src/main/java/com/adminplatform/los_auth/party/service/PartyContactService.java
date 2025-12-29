package com.adminplatform.los_auth.party.service;

import com.adminplatform.los_auth.party.dto.PartyContactDto;
import com.adminplatform.los_auth.party.entity.ContactPoint;
import com.adminplatform.los_auth.party.entity.ContactPointId;
import com.adminplatform.los_auth.party.entity.PartyContact;
import com.adminplatform.los_auth.party.entity.PartyContactId;
import com.adminplatform.los_auth.party.repository.ContactPointRepository;
import com.adminplatform.los_auth.party.repository.PartyContactRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PartyContactService {

    private final PartyContactRepository partyContactRepo;
    private final ContactPointRepository contactPointRepo;

    public PartyContactService(
            PartyContactRepository partyContactRepo,
            ContactPointRepository contactPointRepo
    ) {
        this.partyContactRepo = partyContactRepo;
        this.contactPointRepo = contactPointRepo;
    }

    // ✅ Return DTOs with effective dates
    public List<PartyContactDto> getContacts(UUID tenantId, UUID partyId) {
        return partyContactRepo.findByIdTenantIdAndIdPartyId(tenantId, partyId)
                .stream()
                .map(pc -> new PartyContactDto(
                        pc.getId().getPartyId(),
                        pc.getId().getContactId(),
                        pc.getType(),
                        pc.getId().getEffectiveFrom(),
                        pc.getEffectiveTo(),
                        pc.getContactPoint().getValue(),
                        pc.isPreferred()
                ))
                .collect(Collectors.toList());
    }

    // ✅ Save new contact
    public PartyContactDto addContact(UUID tenantId, UUID partyId, PartyContactDto dto) {
        ContactPointId cpId = new ContactPointId(tenantId, dto.contactId());
        ContactPoint cp = contactPointRepo.findById(cpId)
                .orElseThrow(() -> new RuntimeException("ContactPoint not found"));

        PartyContactId id = new PartyContactId(
                tenantId,
                partyId,
                dto.contactId(),
                dto.effectiveFrom()
        );

        PartyContact pc = new PartyContact();
        pc.setId(id);
        pc.setType(dto.type());
        pc.setContactPoint(cp);
        pc.setEffectiveTo(dto.effectiveTo());
        pc.setPreferred(dto.preferred());

        partyContactRepo.save(pc);

        return dto;
    }

    // ✅ Update existing contact with value
    public PartyContactDto updateContact(UUID tenantId, UUID partyId, PartyContactDto dto) {
        PartyContactId id = new PartyContactId(
                tenantId,
                partyId,
                dto.contactId(),
                dto.effectiveFrom()
        );

        PartyContact pc = partyContactRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("PartyContact not found"));

        pc.setType(dto.type());
        pc.setEffectiveTo(dto.effectiveTo());
        pc.setPreferred(dto.preferred());

        // ✅ Update value in ContactPoint
        ContactPoint cp = pc.getContactPoint();
        if (cp != null && dto.value() != null) {
            cp.setValue(dto.value());
            contactPointRepo.save(cp); // ✅ Save updated contact point
        }

        partyContactRepo.save(pc);
        return dto;
    }

    public void deleteContact(UUID tenantId, UUID partyId, UUID contactId, LocalDate effectiveFrom) {
        PartyContactId id = new PartyContactId(tenantId, partyId, contactId, effectiveFrom);
        partyContactRepo.deleteById(id);
    }


    public PartyContact save(PartyContact pc, UUID tenantId, UUID partyId, LocalDate effectiveFrom) {
        ContactPoint cp = pc.getContactPoint();

        if (cp.getId() == null) {
            ContactPointId cpId = new ContactPointId(tenantId, UUID.randomUUID());
            cp.setId(cpId);
        }

        contactPointRepo.save(cp);

        if (pc.getId() == null) {
            PartyContactId id = new PartyContactId(
                    tenantId,
                    partyId,
                    cp.getId().getContactId(),
                    effectiveFrom
            );
            pc.setId(id);
        }

        return partyContactRepo.save(pc);
    }

    public void delete(PartyContactId id) {
        partyContactRepo.deleteById(id);
    }
}
