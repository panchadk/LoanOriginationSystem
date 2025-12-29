package com.adminplatform.los_auth.party.repository;

import com.adminplatform.los_auth.party.entity.ContactPoint;
import com.adminplatform.los_auth.party.entity.ContactPointId;
import com.adminplatform.los_auth.party.entity.PartyContact;
import com.adminplatform.los_auth.party.entity.PartyContactId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ContactPointRepository extends JpaRepository<ContactPoint, ContactPointId> {}


