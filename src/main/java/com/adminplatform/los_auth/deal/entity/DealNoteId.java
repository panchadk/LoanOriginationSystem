package com.adminplatform.los_auth.deal.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class DealNoteId implements Serializable {
    private UUID tenantId;
    private UUID noteId;

    public DealNoteId() {}

    public DealNoteId(UUID tenantId, UUID noteId) {
        this.tenantId = tenantId;
        this.noteId = noteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DealNoteId)) return false;
        DealNoteId that = (DealNoteId) o;
        return Objects.equals(tenantId, that.tenantId) &&
                Objects.equals(noteId, that.noteId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tenantId, noteId);
    }
}
