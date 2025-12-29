package com.adminplatform.los_auth.party.service;

import com.adminplatform.los_auth.party.dto.ContactPointDto;
import com.adminplatform.los_auth.party.entity.ContactPoint;
import com.adminplatform.los_auth.party.entity.ContactPointId;
import com.adminplatform.los_auth.party.repository.ContactPointRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ContactPointService {

    private final ContactPointRepository contactPointRepo;

    public ContactPointService(ContactPointRepository contactPointRepo) {
        this.contactPointRepo = contactPointRepo;
    }

    public ContactPointDto create(UUID tenantId, ContactPointDto dto) {
        UUID contactId = dto.contactId() != null ? dto.contactId() : UUID.randomUUID();

        ContactPoint cp = new ContactPoint();
        cp.setId(new ContactPointId(tenantId, contactId));
        cp.setType(dto.type());
        cp.setValue(dto.value());
        cp.setVerified(dto.verified() != null ? dto.verified() : false);
        cp.setCreatedAt(dto.createdAt() != null ? dto.createdAt() : LocalDateTime.now());

        contactPointRepo.save(cp);

        return new ContactPointDto(
                contactId,
                cp.getType(),
                cp.getValue(),
                cp.getVerified(),
                cp.getCreatedAt()
        );
    }
}
