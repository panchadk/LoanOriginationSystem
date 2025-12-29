package com.adminplatform.los_auth.party.controller;

import com.adminplatform.los_auth.party.entity.PartyBankAccount;
import com.adminplatform.los_auth.party.service.PartyBankAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/party/{partyId}/bank-account")
public class PartyBankAccountController {

    private final PartyBankAccountService service;

    public PartyBankAccountController(PartyBankAccountService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PartyBankAccount>> getAccounts(
            @RequestHeader("X-Tenant-ID") UUID tenantId,
            @PathVariable UUID partyId
    ) {
        return ResponseEntity.ok(service.getAccounts(tenantId, partyId));
    }

    @PostMapping
    public ResponseEntity<PartyBankAccount> createAccount(
            @RequestHeader("X-Tenant-ID") UUID tenantId,
            @PathVariable UUID partyId,
            @RequestBody PartyBankAccount account
    ) {
        account.setTenantId(tenantId);
        account.setPartyId(partyId);
        account.setBankAccountId(UUID.randomUUID());
        return ResponseEntity.ok(service.saveAccount(account));
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<PartyBankAccount> updateAccount(
            @RequestHeader("X-Tenant-ID") UUID tenantId,
            @PathVariable UUID partyId,
            @PathVariable UUID accountId,
            @RequestBody PartyBankAccount account
    ) {
        account.setTenantId(tenantId);
        account.setPartyId(partyId);
        account.setBankAccountId(accountId);
        return ResponseEntity.ok(service.saveAccount(account));
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteAccount(
            @RequestHeader("X-Tenant-ID") UUID tenantId,
            @PathVariable UUID accountId
    ) {
        service.deleteAccount(accountId);
        return ResponseEntity.noContent().build();
    }
}
