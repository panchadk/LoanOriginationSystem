package com.adminplatform.los_auth.party.repository;

import com.adminplatform.los_auth.party.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrganizationRepository extends JpaRepository<Organization, UUID> {
}
