package com.adminplatform.los_auth.deal.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "deal_stage")
public class DealStage {

    @Id
    private UUID stageId;

    @Embedded
    private DealId dealId;

    private String fromStage;
    private String toStage;
    private String stageType;
    private UUID triggeredBy;
    private LocalDateTime triggeredAt;
    private String notes;

    @PrePersist
    public void onCreate() {
        this.stageId = UUID.randomUUID();
        this.triggeredAt = LocalDateTime.now();
    }

    // Getters and setters...

    public UUID getStageId() {
        return stageId;
    }

    public void setStageId(UUID stageId) {
        this.stageId = stageId;
    }

    public DealId getDealId() {
        return dealId;
    }


    public String getFromStage() {
        return fromStage;
    }

    public void setFromStage(String fromStage) {
        this.fromStage = fromStage;
    }

    public String getToStage() {
        return toStage;
    }

    public void setToStage(String toStage) {
        this.toStage = toStage;
    }

    public String getStageType() {
        return stageType;
    }

    public void setStageType(String stageType) {
        this.stageType = stageType;
    }

    public UUID getTriggeredBy() {
        return triggeredBy;
    }

    public void setTriggeredBy(UUID triggeredBy) {
        this.triggeredBy = triggeredBy;
    }

    public LocalDateTime getTriggeredAt() {
        return triggeredAt;
    }

    public void setTriggeredAt(LocalDateTime triggeredAt) {
        this.triggeredAt = triggeredAt;
    }

    public String getNotes() {
        return notes;
    }

    public void setDealId(DealId dealId) {
        this.dealId = dealId;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }


}
