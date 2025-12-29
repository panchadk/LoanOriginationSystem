package com.adminplatform.los_auth.deal.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class DealSummaryId implements Serializable {
    private UUID tenantId;
    private UUID dealId;

    public DealSummaryId() {}

    public DealSummaryId(UUID tenantId, UUID dealId) {
        this.tenantId = tenantId;
        this.dealId = dealId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DealSummaryId)) return false;
        DealSummaryId that = (DealSummaryId) o;
        return Objects.equals(tenantId, that.tenantId) &&
                Objects.equals(dealId, that.dealId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tenantId, dealId);
    }
}
