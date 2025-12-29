package com.adminplatform.los_auth.party.repository;

import com.adminplatform.los_auth.party.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {

    // Optional: find all addresses for a tenant
    List<Address> findByTenantId(UUID tenantId);

    // Optional: find by tenant and postal code
    List<Address> findByTenantIdAndPostalCode(UUID tenantId, String postalCode);

    // Optional: deduplication support
    List<Address> findByTenantIdAndLine1AndCityAndPostalCode(UUID tenantId, String line1, String city, String postalCode);
}
