package com.adminplatform.los_auth.party.service;

import com.adminplatform.los_auth.audit.entity.AuditEvent;
import com.adminplatform.los_auth.audit.repository.AuditEventRepository;
import com.adminplatform.los_auth.outbox.entity.OutboxEvent;
import com.adminplatform.los_auth.outbox.repository.OutboxEventRepository;
import com.adminplatform.los_auth.party.dto.PartyCreateRequestDto;
import com.adminplatform.los_auth.party.dto.PartyCreatedEvent;
import com.adminplatform.los_auth.party.dto.PartyDetailDto;
import com.adminplatform.los_auth.party.dto.PartySummaryDto;
import com.adminplatform.los_auth.party.entity.Organization;
import com.adminplatform.los_auth.party.entity.Party;
import com.adminplatform.los_auth.party.entity.PartyId;
import com.adminplatform.los_auth.party.entity.Person;
import com.adminplatform.los_auth.party.repository.OrganizationRepository;
import com.adminplatform.los_auth.party.repository.PartyRepository;
import com.adminplatform.los_auth.party.repository.PersonRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;



import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.slf4j.LoggerFactory;
import java.util.stream.Collectors;

@Service
public class PartyService {
    private static final Logger log = LoggerFactory.getLogger(PartyService.class);
    private final PartyRepository partyRepository;
    private final PersonRepository personRepository;
    private final OrganizationRepository organizationRepository;
    private final OutboxEventRepository outboxEventRepository;
    private final AuditEventRepository auditEventRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public PartyService(

            PartyRepository partyRepository,
            PersonRepository personRepository,
            OrganizationRepository organizationRepository,
            OutboxEventRepository outboxEventRepository,
            AuditEventRepository auditEventRepository,
            ObjectMapper objectMapper
    ) {
        this.partyRepository = partyRepository;
        this.personRepository = personRepository;
        this.organizationRepository = organizationRepository;
        this.outboxEventRepository = outboxEventRepository;
        this.auditEventRepository = auditEventRepository;
        this.objectMapper = objectMapper;
    }

    public List<PartySummaryDto> searchParties(UUID tenantId, String search) {
        //Logger.info("Searching parties for tenant ID: {} with search term: {}", tenantId, search);

        List<Party> parties = partyRepository.searchParties(tenantId, search);
       // log.info("Found {} parties", parties.size());

        return parties.stream()
                .map(p -> {
                log.info("Mapping party ID: {}, Kind: {}, Status: {}", p.getPartyId(), p.getKind(), p.getStatus());
                    return new PartySummaryDto(
                            p.getPartyId(),
                            p.getKind(),
                            p.getStatus(),
                            p.getCreatedAt().toString(),
                            p.getPerson() != null ? p.getPerson().getGivenName() : null,
                            p.getPerson() != null ? p.getPerson().getFamilyName() : null,
                            p.getOrganization() != null ? p.getOrganization().getLegalName() : null
                    );
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public UUID createParty(UUID tenantId, PartyCreateRequestDto request) {
        try {
            log.debug("Creating party with tenant ID: {}, request: {}", tenantId, request);

            UUID partyId = UUID.randomUUID();
            log.debug("Generated party ID: {}", partyId);

            Party party = new Party();
            party.setTenantId(tenantId);
            party.setPartyId(partyId);
            party.setKind(request.getKind());
            party.setStatus("ACTIVE");
            party.setCreatedAt(Instant.now());

            // Save the Party entity first
            log.debug("Saving party entity: {}", party);
            party = partyRepository.saveAndFlush(party);

            if ("PERSON".equalsIgnoreCase(request.getKind())) {
                Person person = new Person();
                person.setTenantId(tenantId);
                person.setPartyId(partyId);        // ✅ set composite PK
                person.setParty(party);            // ✅ link to Party
                person.setGivenName(request.getGivenName());
                person.setFamilyName(request.getFamilyName());
                person.setMiddleName(request.getMiddleName());
                person.setDob(request.getDob());
                person.setResidencyStatus(request.getResidencyStatus());

                log.debug("Saving person entity: {}", person);
                personRepository.saveAndFlush(person);
            } else if ("ORGANIZATION".equalsIgnoreCase(request.getKind())) {
                Organization org = new Organization();
                org.setTenantId(tenantId);
                org.setPartyId(partyId);           // ✅ set composite PK
                org.setParty(party);               // ✅ link to Party
                org.setLegalName(request.getLegalName());
                org.setRegistrationJurisdiction(request.getRegistrationJurisdiction());
                org.setBusinessType(request.getBusinessType());
                org.setBin(request.getBin());
                org.setCreatedAt(Instant.now());

                log.debug("Saving organization entity: {}", org);
                organizationRepository.saveAndFlush(org);
            } else {
                throw new IllegalArgumentException("Invalid party kind: " + request.getKind());
            }

            PartyCreatedEvent event = new PartyCreatedEvent(
                    party.getPartyId(),
                    party.getTenantId(),
                    party.getKind(),
                    party.getStatus(),
                    deriveDisplayName(party)
            );

            log.debug("Logging outbox event: {}", event);
            logOutboxEvent(tenantId, "PARTY_CREATED", "Party", partyId, event);

            log.debug("Logging audit event: {}", event);
            logAuditEvent(tenantId, "Party", partyId, "PARTY_CREATED", event);

            return partyId;
        } catch (Exception e) {
            log.error("Error creating party", e);
            throw new RuntimeException("Failed to create party", e);
        }
    }

    private String deriveDisplayName(Party party) {
        if ("PERSON".equalsIgnoreCase(party.getKind()) && party.getPerson() != null) {
            String given = party.getPerson().getGivenName();
            String family = party.getPerson().getFamilyName();
            return ((given != null ? given : "") + " " + (family != null ? family : "")).trim();
        } else if ("ORGANIZATION".equalsIgnoreCase(party.getKind()) && party.getOrganization() != null) {
            return party.getOrganization().getLegalName() != null
                    ? party.getOrganization().getLegalName()
                    : "Unnamed Organization";
        }
        return "Unknown Party";
    }

    public void updateParty(UUID tenantId, UUID partyId, PartyCreateRequestDto request) {
        // Fetch the existing party
        Party party = partyRepository.findById(new PartyId(tenantId, partyId))
                .orElseThrow(() -> new EntityNotFoundException("Party not found"));

        // Validate tenant ownership
        if (!party.getTenantId().equals(tenantId)) {
            throw new AccessDeniedException("Tenant mismatch");
        }

        // Update common fields in the Party entity
        party.setKind(request.getKind());
        party.setStatus("ACTIVE");
        party.setUpdatedAt(Instant.now()); // Use updatedAt instead of overwriting createdAt

        // Update nested entity (Person or Organization)
        if ("PERSON".equalsIgnoreCase(request.getKind())) {
            Person person = party.getPerson();
            if (person == null) {
                throw new IllegalArgumentException("Cannot update a non-PERSON party as PERSON");
            }

            // Update Person fields
            person.setGivenName(request.getGivenName());
            person.setFamilyName(request.getFamilyName());
            person.setMiddleName(request.getMiddleName());
            person.setDob(request.getDob());
            person.setResidencyStatus(request.getResidencyStatus());
            personRepository.save(person);
        } else if ("ORGANIZATION".equalsIgnoreCase(request.getKind())) {
            Organization org = party.getOrganization();
            if (org == null) {
                throw new IllegalArgumentException("Cannot update a non-ORGANIZATION party as ORGANIZATION");
            }

            // Update Organization fields
            org.setLegalName(request.getLegalName());
            org.setRegistrationJurisdiction(request.getRegistrationJurisdiction());
            org.setBusinessType(request.getBusinessType());
            org.setBin(request.getBin());
            organizationRepository.save(org);
        } else {
            throw new IllegalArgumentException("Invalid party kind: " + request.getKind());
        }

        // Save the updated Party entity
        partyRepository.save(party);

        // Log the update event
        PartyCreatedEvent event = new PartyCreatedEvent(
                party.getPartyId(),
                party.getTenantId(),
                party.getKind(),
                party.getStatus(),
                deriveDisplayName(party)
        );

        logOutboxEvent(tenantId, "PARTY_UPDATED", "Party", partyId, event);
        logAuditEvent(tenantId, "Party", partyId, "PARTY_UPDATED", event);
    }

    public void updateStatus(UUID tenantId, UUID partyId, String status) {
        Party party = partyRepository.findById(new PartyId(tenantId, partyId))

                .orElseThrow(() -> new EntityNotFoundException("Party not found"));

        if (!party.getTenantId().equals(tenantId)) {
            throw new AccessDeniedException("Tenant mismatch");
        }

        String normalizedStatus = status.toUpperCase();
        if (!normalizedStatus.equals("ACTIVE") && !normalizedStatus.equals("INACTIVE")) {
            throw new IllegalArgumentException("Status must be ACTIVE or INACTIVE");
        }

        party.setStatus(normalizedStatus);
        partyRepository.save(party);

        PartyCreatedEvent event = new PartyCreatedEvent(
                party.getPartyId(),
                party.getTenantId(),
                party.getKind(),
                party.getStatus(),
                deriveDisplayName(party)
        );

        logOutboxEvent(tenantId, "STATUS_UPDATED_" + normalizedStatus, "Party", partyId, event);
        logAuditEvent(tenantId, "Party", partyId, "STATUS_UPDATED_" + normalizedStatus, event);
    }

    public PartyDetailDto getPartyDetails(UUID tenantId, UUID partyId) {
        log.info("Fetching party details for tenant ID: {}, party ID: {}", tenantId, partyId);

        Party party = partyRepository.findById(new PartyId(tenantId, partyId))
                .orElseThrow(() -> new EntityNotFoundException("Party not found"));

        log.info("Found party with ID: {}, Kind: {}, Status: {}",
                party.getPartyId(), party.getKind(), party.getStatus());

        if (!party.getTenantId().equals(tenantId)) {
            log.error("Tenant mismatch for party ID: {}", partyId);
            throw new AccessDeniedException("Tenant mismatch");
        }

        PartyDetailDto dto = new PartyDetailDto();
        dto.setPartyId(party.getPartyId());
        dto.setKind(party.getKind());
        dto.setStatus(party.getStatus());
        dto.setCreatedAt(party.getCreatedAt());

        if ("PERSON".equalsIgnoreCase(party.getKind())) {
            Person person = party.getPerson();
            if (person == null) {
                log.warn("Person is null for party ID: {}", partyId);
            } else {
                log.info("Mapping person details for party ID: {}", partyId);
                dto.setGivenName(person.getGivenName());
                dto.setFamilyName(person.getFamilyName());
                dto.setMiddleName(person.getMiddleName());
                dto.setResidencyStatus(person.getResidencyStatus());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
                dto.setDob(person.getDob() != null ? person.getDob().format(formatter) : null);
            }
        }

        if ("ORGANIZATION".equalsIgnoreCase(party.getKind())) {
            Organization org = party.getOrganization();
            if (org == null) {
                log.warn("Organization is null for party ID: {}", partyId);
            } else {
                log.info("Mapping organization details for party ID: {}", partyId);
                dto.setLegalName(org.getLegalName());
                dto.setRegistrationJurisdiction(org.getRegistrationJurisdiction());
                dto.setBusinessType(org.getBusinessType());
                dto.setBin(org.getBin());
            }
        }

        log.info("Returning party details: {}", dto);
        return dto;
    }

    public List<AuditEvent> getAuditTrail(UUID tenantId, UUID partyId) {
        return auditEventRepository.findByTenantIdAndEntityIdOrderByOccurredAtDesc(tenantId, partyId);
    }

    public void deleteParty(UUID tenantId, UUID partyId) {
        Party party = partyRepository.findById(new PartyId(tenantId, partyId))
                .orElseThrow(() -> new EntityNotFoundException("Party not found"));

        if (!party.getTenantId().equals(tenantId)) {
            throw new AccessDeniedException("Tenant mismatch");
        }

        party.setStatus("INACTIVE");
        partyRepository.save(party);
    }

    private void logOutboxEvent(UUID tenantId, String eventType, String aggregateType, UUID aggregateId, Object payload) {
        OutboxEvent event = new OutboxEvent();
        event.setOutboxId(UUID.randomUUID());
        event.setTenantId(tenantId);
        event.setEventType(eventType);
        event.setAggregateType(aggregateType);
        event.setAggregateId(aggregateId);
        event.setPayload(toJson(payload));
        event.setCreatedAt(Instant.now());
        outboxEventRepository.save(event);
    }

    private void logAuditEvent(UUID tenantId, String entityType, UUID entityId, String eventType, Object payload) {
        AuditEvent audit = new AuditEvent();
        audit.setAuditId(UUID.randomUUID());
        audit.setTenantId(tenantId);
        audit.setEntityType(entityType);
        audit.setEntityId(entityId);
        audit.setEventType(eventType);
        audit.setOccurredAt(Instant.now());
        audit.setPayload(toJson(payload));
        auditEventRepository.save(audit);
    }

    private String toJson(@NotNull Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize payload", e);
        }
    }
}