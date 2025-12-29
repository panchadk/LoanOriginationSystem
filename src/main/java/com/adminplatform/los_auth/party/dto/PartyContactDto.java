package com.adminplatform.los_auth.party.dto;

import java.time.LocalDate;
import java.util.UUID;

public record PartyContactDto(
        UUID partyId,
        UUID contactId,
        String type,
        LocalDate effectiveFrom,
        LocalDate effectiveTo,
        String value,
        boolean preferred
) {}

