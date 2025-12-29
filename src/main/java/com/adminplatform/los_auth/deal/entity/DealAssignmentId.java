package com.adminplatform.los_auth.deal.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class DealAssignmentId implements Serializable {
    private UUID tenantId;
    private UUID assignmentId;

    public DealAssignmentId() {}

    public DealAssignmentId(UUID tenantId, UUID assignmentId) {
        this.tenantId = tenantId;
        this.assignmentId = assignmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DealAssignmentId)) return false;
        DealAssignmentId that = (DealAssignmentId) o;
        return Objects.equals(tenantId, that.tenantId) &&
                Objects.equals(assignmentId, that.assignmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tenantId, assignmentId);
    }
}
