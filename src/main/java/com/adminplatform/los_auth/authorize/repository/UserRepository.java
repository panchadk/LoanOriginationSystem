package com.adminplatform.los_auth.authorize.repository;

import com.adminplatform.los_auth.authorize.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserAccount, UUID> {
    // Custom query methods can be added here if needed
}