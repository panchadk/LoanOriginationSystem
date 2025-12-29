package com.adminplatform.los_auth.party.repository;

import com.adminplatform.los_auth.party.entity.Party;
import com.adminplatform.los_auth.party.entity.PartyId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PartyRepository extends JpaRepository<Party, PartyId> {

    @Query("""
        SELECT p FROM Party p
        LEFT JOIN FETCH p.person pe
        LEFT JOIN FETCH p.organization o
        WHERE p.tenantId = :tenantId
          AND (
            LOWER(pe.givenName) LIKE LOWER(CONCAT('%', :search, '%')) OR
            LOWER(pe.familyName) LIKE LOWER(CONCAT('%', :search, '%')) OR
            LOWER(o.legalName) LIKE LOWER(CONCAT('%', :search, '%')) OR
            CAST(p.partyId AS string) LIKE CONCAT('%', :search, '%')
          )
        ORDER BY p.createdAt DESC
    """)
    List<Party> searchParties(UUID tenantId, String search);
}
