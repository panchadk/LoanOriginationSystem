package com.adminplatform.los_auth.deal.service;

import com.adminplatform.los_auth.deal.dto.DealSummaryDto;
import com.adminplatform.los_auth.deal.entity.*;
import com.adminplatform.los_auth.deal.repository.*;
import com.adminplatform.los_auth.party.entity.Party;
import com.adminplatform.los_auth.party.entity.PartyId;
import com.adminplatform.los_auth.party.repository.PartyRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DealService {

    private final DealRepository dealRepository;
    private final DealNoteRepository dealNoteRepository;
    private final DealStageRepository dealStageRepository;
    private final BorrowerRepository borrowerRepository;
    private final BrokerRepository brokerRepository;
    private final GuarantorRepository guarantorRepository;
    private final PartyRepository partyRepository;   // ✅ inject PartyRepository

    public DealService(DealRepository dealRepository,
                       DealNoteRepository dealNoteRepository,
                       DealStageRepository dealStageRepository,
                       BorrowerRepository borrowerRepository,
                       BrokerRepository brokerRepository,
                       GuarantorRepository guarantorRepository,
                       PartyRepository partyRepository) {
        this.dealRepository = dealRepository;
        this.dealNoteRepository = dealNoteRepository;
        this.dealStageRepository = dealStageRepository;
        this.borrowerRepository = borrowerRepository;
        this.brokerRepository = brokerRepository;
        this.guarantorRepository = guarantorRepository;
        this.partyRepository = partyRepository;   // ✅ assign
    }

    // List all deals for a tenant
    public List<Deal> getDeals(UUID tenantId) {
        return dealRepository.findByTenantId(tenantId);
    }

    // Get a single deal by tenant + dealId
    public Optional<Deal> getDealById(UUID tenantId, UUID dealId) {
        return dealRepository.findById(new DealId(tenantId, dealId));
    }

    // Create a new deal (tenant + user required)
    public Deal createDeal(UUID tenantId, UUID userId, Deal deal) {
        deal.setTenantId(tenantId);
        deal.setCreatedBy(userId);
        deal.setCreatedAt(LocalDateTime.now());

        if (deal.getStageEnteredAt() == null) {
            deal.setStageEnteredAt(LocalDateTime.now());
        }
        if (deal.getStageVersion() == 0) {
            deal.setStageVersion(1);
        }

        return dealRepository.save(deal);
    }

    // Update stage + amount + add note + log stage transition
    public Deal updateStage(UUID tenantId,
                            UUID dealId,
                            UUID userId,
                            String newStage,
                            String notes,
                            String amount) {

        Deal deal = dealRepository.findById(new DealId(tenantId, dealId))
                .orElseThrow(() -> new RuntimeException("Deal not found"));

        String oldStage = deal.getStage();

        // Update stage
        deal.setStage(newStage);
        deal.setStageVersion(deal.getStageVersion() + 1);
        deal.setStageEnteredAt(LocalDateTime.now());

        // Update amount if provided
        if (amount != null && !amount.isBlank()) {
            try {
                deal.setAmountRequested(new BigDecimal(amount));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid amount format: " + amount);
            }
        }

        // Save deal first
        Deal savedDeal = dealRepository.save(deal);

        // Add note if provided
        if (notes != null && !notes.isBlank()) {
            DealNote note = new DealNote();
            note.setTenantId(tenantId);
            note.setDealId(dealId);
            note.setBody(notes);
            note.setCreatedAt(LocalDateTime.now());

            note.setNoteId(UUID.randomUUID());
            note.setAuthorUserId(userId);
            note.setVisibility("INTERNAL");

            dealNoteRepository.save(note);
        }

        // Log stage transition in deal_stage table
        DealStage stageLog = new DealStage();
        stageLog.setStageId(UUID.randomUUID());
        stageLog.setDealId(new DealId(tenantId, dealId));
        stageLog.setFromStage(oldStage);
        stageLog.setToStage(newStage);
        stageLog.setStageType("AdvanceWorkStage");
        stageLog.setTriggeredBy(userId);
        stageLog.setTriggeredAt(LocalDateTime.now());
        stageLog.setNotes(notes);

        dealStageRepository.save(stageLog);

        return savedDeal;
    }

    // Group deals by stage for Kanban
    public Map<String, List<DealSummaryDto>> getDealsGroupedByStage(UUID tenantId) {
        List<Deal> deals = dealRepository.findByTenantId(tenantId);

        return deals.stream()
                .collect(Collectors.groupingBy(
                        Deal::getStage,
                        Collectors.mapping(
                                d -> new DealSummaryDto(
                                        d.getDealId(),
                                        d.getAmountRequested(),
                                        d.getStageVersion(),
                                        d.getProductCode()
                                ),
                                Collectors.toList()
                        )
                ));
    }

    // --- NEW METHODS ---

    public Borrower addBorrowerToDeal(UUID tenantId, UUID userId, UUID dealId, UUID partyId, Borrower borrower) {
        Deal deal = dealRepository.findById(new DealId(tenantId, dealId))
                .orElseThrow(() -> new RuntimeException("Deal not found"));

        // ensure party exists
        Party party = partyRepository.findById(new PartyId(tenantId, partyId))
                .orElseThrow(() -> new RuntimeException("Party not found"));

        borrower.setBorrowerId(UUID.randomUUID());
        borrower.setTenantId(tenantId);
        borrower.setDealId(dealId);
        borrower.setPartyId(partyId);
        borrower.setCreatedBy(userId);
        borrower.setCreatedAt(LocalDateTime.now());
        borrower.setDeal(deal);
        borrower.setParty(party);

        return borrowerRepository.save(borrower);
    }

    public Broker addBrokerToDeal(UUID tenantId, UUID userId, UUID dealId, Broker broker) {
        Deal deal = dealRepository.findById(new DealId(tenantId, dealId))
                .orElseThrow(() -> new RuntimeException("Deal not found"));

        broker.setDeal(deal);
        broker.setCreatedBy(userId);

        broker.setTenantId(tenantId);
        broker.setDealId(dealId);

        return brokerRepository.save(broker);
    }

    public Guarantor addGuarantorToDeal(UUID tenantId, UUID userId, UUID dealId, Guarantor guarantor) {
        Deal deal = dealRepository.findById(new DealId(tenantId, dealId))
                .orElseThrow(() -> new RuntimeException("Deal not found"));

        guarantor.setDeal(deal);
        guarantor.setCreatedBy(userId);

        guarantor.setTenantId(tenantId);
        guarantor.setDealId(dealId);

        return guarantorRepository.save(guarantor);
    }
}
