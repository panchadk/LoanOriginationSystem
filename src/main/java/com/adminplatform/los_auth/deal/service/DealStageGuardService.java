package com.adminplatform.los_auth.deal.service;

import com.adminplatform.los_auth.deal.entity.Deal;
import com.adminplatform.los_auth.deal.entity.DealParty;
import com.adminplatform.los_auth.deal.entity.Property;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DealStageGuardService {

    public boolean canMoveToPreQual(Deal deal, List<DealParty> parties, List<Property> properties) {
        return hasBorrower(parties) && !properties.isEmpty() && deal.getAssignedToUserId() != null;
    }

    public boolean canMoveToUnderReview(List<Deal.VerificationRecord> records) {
        return records.stream().anyMatch(r -> "CREDIT_REPORT".equals(r.getType()));
    }

    public boolean canMoveToCommitmentOut(Deal deal) {
        return deal.isAllPtdSatisfied() &&
                deal.isAppraisalAccepted() &&
                deal.isExceptionsApproved() &&
                deal.isPricingResolved();
    }

    public boolean canMoveToPreFunding(Deal.Commitment commitment) {
        return commitment != null && "SIGNED".equals(commitment.getStatus());
    }

    public boolean canMoveToFunded(Deal deal) {
        return deal.isAllPtfSatisfied() &&
                deal.isDepositCleared() &&
                deal.getParticipationPercent() == 100 &&
                deal.hasValidTitleInsurance() &&
                deal.hasValidPropertyInsurance() &&
                deal.hasBalancedFundingSettlement() &&
                deal.isLoanContractComputed();
    }

    public boolean canMoveToComplianceReview(Deal deal) {
        return "FUNDED".equals(deal.getStage());
    }

    private boolean hasBorrower(List<DealParty> parties) {
        return parties.stream().anyMatch(p -> "BORROWER".equals(p.getId().getRoleCode()));
    }
}
