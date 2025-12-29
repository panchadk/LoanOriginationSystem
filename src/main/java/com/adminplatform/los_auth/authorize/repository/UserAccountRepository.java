package com.adminplatform.los_auth.authorize.repository;

import com.adminplatform.los_auth.authorize.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserAccountRepository extends JpaRepository<UserAccount, UUID> {
    Optional<UserAccount> findByEmailIgnoreCase(String email);
    List<UserAccount> findByTenantId(UUID tenantId);

}
