package com.adminplatform.los_auth.party.repository;

import com.adminplatform.los_auth.party.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PersonRepository extends JpaRepository<Person, UUID> {
}
