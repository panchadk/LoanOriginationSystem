package com.adminplatform.los_auth.deal.repository;

import com.adminplatform.los_auth.deal.entity.Broker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BrokerRepository extends JpaRepository<Broker, UUID> {
}
