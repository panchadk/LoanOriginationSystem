package com.adminplatform.los_auth.deal.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class DealTagId implements Serializable {
    private UUID tenantId;
    private UUID dealId;
    private String tag;

    public DealTagId() {}

    public DealTagId(UUID tenantId, UUID dealId, String tag) {
        this.tenantId = tenantId;
        this.dealId = dealId;
        this.tag = tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DealTagId)) return false;
        DealTagId that = (DealTagId) o;
        return Objects.equals(tenantId, that.tenantId) &&
                Objects.equals(dealId, that.dealId) &&
                Objects.equals(tag, that.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tenantId, dealId, tag);
    }
}
