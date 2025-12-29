package com.adminplatform.los_auth.audit.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "audit_event")
public class AuditEvent {

    @Id
    @Column(name = "audit_id", nullable = false)
    private UUID auditId;

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Column(name = "entity_type", nullable = false)
    private String entityType;

    @Column(name = "entity_id", nullable = false)
    private UUID entityId;

    @Column(name = "event_type", nullable = false)
    private String eventType;

    @Column(name = "occurred_at", nullable = false)
    private Instant occurredAt;

    @JdbcTypeCode(SqlTypes.JSON) // Use JSON type for Hibernate 6
    @Column(name = "payload", columnDefinition = "jsonb")
    private String payload; // Store payload as JSON string

    @Column(name = "idempotency_key")
    private String idempotencyKey;

    @Column(name = "actor_user_id")
    private UUID actorUserId; // Ensure this is UUID

    @Column(name = "actor_session_id")
    private UUID actorSessionId; // Ensure this is UUID

    @Column(name = "request_id")
    private UUID requestId; // Ensure this is UUID

    // Getters and Setters
    public UUID getAuditId() {
        return auditId;
    }

    public void setAuditId(UUID auditId) {
        this.auditId = auditId;
    }

    public UUID getTenantId() {
        return tenantId;
    }

    public void setTenantId(UUID tenantId) {
        this.tenantId = tenantId;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public UUID getEntityId() {
        return entityId;
    }

    public void setEntityId(UUID entityId) {
        this.entityId = entityId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Instant getOccurredAt() {
        return occurredAt;
    }

    public void setOccurredAt(Instant occurredAt) {
        this.occurredAt = occurredAt;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            this.payload = objectMapper.writeValueAsString(payload); // Serialize object to JSON string
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize payload", e);
        }
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public void setIdempotencyKey(String idempotencyKey) {
        this.idempotencyKey = idempotencyKey;
    }

    public UUID getActorUserId() {
        return actorUserId;
    }

    public void setActorUserId(UUID actorUserId) {
        this.actorUserId = actorUserId;
    }

    public UUID getActorSessionId() {
        return actorSessionId;
    }

    public void setActorSessionId(UUID actorSessionId) {
        this.actorSessionId = actorSessionId;
    }

    public UUID getRequestId() {
        return requestId;
    }

    public void setRequestId(UUID requestId) {
        this.requestId = requestId;
    }

    // Utility method to deserialize payload
    public <T> T getPayloadAs(Class<T> clazz) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(payload, clazz); // Deserialize JSON string to object
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize payload", e);
        }
    }
}