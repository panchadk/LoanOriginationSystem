package com.adminplatform.los_auth.party.dto;

import com.fasterxml.jackson.databind.JsonNode;

import java.time.LocalDate;
import java.util.UUID;

public record PartyAddressDto(
        UUID partyId,
        UUID addressId,
        String kind,
        LocalDate effectiveFrom,
        LocalDate effectiveTo,
        String line1,
        String line2,
        String city,
        String provinceCode,
        String postalCode,
        String countryCode,
        JsonNode geocode// ✅ correct

) {}

