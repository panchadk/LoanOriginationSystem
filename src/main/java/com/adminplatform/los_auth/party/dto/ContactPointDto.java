package com.adminplatform.los_auth.party.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ContactPointDto(
        UUID contactId,
        String type,
        String value,
        Boolean verified,
        LocalDateTime createdAt
) {}
