package com.adminplatform.los_auth.deal.repository;

import com.adminplatform.los_auth.deal.entity.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BorrowerRepository extends JpaRepository<Borrower, UUID> {
}
