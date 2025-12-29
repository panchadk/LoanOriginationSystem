package com.adminplatform.los_auth.deal.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class DealSummaryDto {
    private UUID dealId;
    private BigDecimal amountRequested;
    private int stageVersion;
    private String lastDecision;

    public DealSummaryDto(UUID dealId, BigDecimal amountRequested, int stageVersion, String lastDecision) {
        this.dealId = dealId;
        this.amountRequested = amountRequested;
        this.stageVersion = stageVersion;
        this.lastDecision = lastDecision;
    }

    public UUID getDealId() { return dealId; }
    public BigDecimal getAmountRequested() { return amountRequested; }
    public int getStageVersion() { return stageVersion; }
    public String getLastDecision() { return lastDecision; }
}
