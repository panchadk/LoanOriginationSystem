package com.adminplatform.los_auth.authorize.repository;

import com.adminplatform.los_auth.authorize.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, String> {
    Optional<PasswordResetToken> findByToken(String token);
    Optional<PasswordResetToken> findByUser(com.adminplatform.los_auth.authorize.entity.UserAccount user);

}
