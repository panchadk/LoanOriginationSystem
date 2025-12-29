package com.adminplatform.los_auth.deal.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class DealPartyRequestDto {
    public UUID partyId;
    public String roleCode;
    public BigDecimal liabilityPct;
    public String guaranteeType;
    public BigDecimal guaranteeCapAmount;
    public LocalDate effectiveFrom;
    public LocalDate effectiveTo;
}
