package com.adminplatform.los_auth.deal.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class DealDecisionId implements Serializable {
    private UUID tenantId;
    private UUID decisionId;

    public DealDecisionId() {}

    public DealDecisionId(UUID tenantId, UUID decisionId) {
        this.tenantId = tenantId;
        this.decisionId = decisionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DealDecisionId)) return false;
        DealDecisionId that = (DealDecisionId) o;
        return Objects.equals(tenantId, that.tenantId) &&
                Objects.equals(decisionId, that.decisionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tenantId, decisionId);
    }
}
