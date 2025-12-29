package com.adminplatform.los_auth.deal.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "deal_note")
public class DealNote {

    @Id
    @GeneratedValue
    @Column(name = "note_id", nullable = false, updatable = false)
    private UUID noteId;

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Column(name = "deal_id", nullable = false)
    private UUID dealId;

    @Column(name = "author_user_id", nullable = false)
    private UUID authorUserId;

    @Column(name = "visibility", nullable = false, length = 20)
    private String visibility; // INTERNAL or BROKER

    @Column(name = "body", nullable = false, length = 2000)
    private String body;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void onCreate() {
        createdAt = LocalDateTime.now();
        if (noteId == null) {
            noteId = UUID.randomUUID();
        }
    }

    // --- Getters & Setters ---
    public UUID getNoteId() { return noteId; }
    public void setNoteId(UUID noteId) { this.noteId = noteId; }

    public UUID getTenantId() { return tenantId; }
    public void setTenantId(UUID tenantId) { this.tenantId = tenantId; }

    public UUID getDealId() { return dealId; }
    public void setDealId(UUID dealId) { this.dealId = dealId; }

    public UUID getAuthorUserId() { return authorUserId; }
    public void setAuthorUserId(UUID authorUserId) { this.authorUserId = authorUserId; }

    public String getVisibility() { return visibility; }
    public void setVisibility(String visibility) { this.visibility = visibility; }

    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
