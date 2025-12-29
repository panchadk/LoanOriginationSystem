package com.adminplatform.los_auth.deal.service;

import com.adminplatform.los_auth.deal.entity.DealAssignment;
import com.adminplatform.los_auth.deal.entity.DealSummaryId;
import com.adminplatform.los_auth.deal.repository.DealAssignmentRepository;
import com.adminplatform.los_auth.deal.repository.DealSummaryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DealAssignmentService {

    private final DealAssignmentRepository dealAssignmentRepository;
    private final DealSummaryRepository dealSummaryRepository;

    public DealAssignmentService(DealAssignmentRepository dealAssignmentRepository,
                                 DealSummaryRepository dealSummaryRepository) {
        this.dealAssignmentRepository = dealAssignmentRepository;
        this.dealSummaryRepository = dealSummaryRepository;
    }

    public DealAssignment assignDeal(UUID tenantId,
                                     UUID dealId,
                                     UUID userId,
                                     UUID queueId,
                                     String reason) {
        // End any existing open assignment
        List<DealAssignment> openAssignments =
                dealAssignmentRepository.findByTenantIdAndDealIdAndEndedAtIsNull(tenantId, dealId);
        for (DealAssignment assignment : openAssignments) {
            assignment.setEndedAt(LocalDateTime.now());
            dealAssignmentRepository.save(assignment);
        }

        // Create new assignment
        DealAssignment newAssignment = new DealAssignment();
        newAssignment.setTenantId(tenantId);
        newAssignment.setAssignmentId(UUID.randomUUID());
        newAssignment.setDealId(dealId);
        newAssignment.setUserId(userId);
        newAssignment.setQueueId(queueId);
        newAssignment.setReason(reason);
        newAssignment.setAssignedAt(LocalDateTime.now());

        DealAssignment saved = dealAssignmentRepository.save(newAssignment);

        // Update summary row
        updateSummaryAssignment(tenantId, dealId, userId, queueId);

        return saved;
    }

    public List<DealAssignment> getOpenAssignments(UUID tenantId, UUID dealId) {
        return dealAssignmentRepository.findByTenantIdAndDealIdAndEndedAtIsNull(tenantId, dealId);
    }

    private void updateSummaryAssignment(UUID tenantId, UUID dealId, UUID userId, UUID queueId) {
        dealSummaryRepository.findById(new DealSummaryId(tenantId, dealId)).ifPresent(summary -> {
            // Prefer user assignment, fallback to queue
            summary.setAssignedTo(userId != null ? userId : queueId);
            summary.setLastUpdatedAt(LocalDateTime.now());
            dealSummaryRepository.save(summary);
        });
    }
}
