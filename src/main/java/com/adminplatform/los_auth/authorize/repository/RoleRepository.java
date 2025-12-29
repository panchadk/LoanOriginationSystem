package com.adminplatform.los_auth.authorize.repository;

import com.adminplatform.los_auth.authorize.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepository extends CrudRepository<Role, UUID> {
}
