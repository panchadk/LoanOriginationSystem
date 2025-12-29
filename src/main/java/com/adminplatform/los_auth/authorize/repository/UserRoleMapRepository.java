package com.adminplatform.los_auth.authorize.repository;

import com.adminplatform.los_auth.authorize.entity.UserRoleMap;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRoleMapRepository extends CrudRepository<UserRoleMap, UUID> {

    // Native query to fetch role names by user ID
   /* @Query(value = """
        SELECT rm.role_name
        FROM user_role_map urm
        JOIN role_master rm ON urm.role_id = rm.role_id
        WHERE urm.user_id = :userId
        """, nativeQuery = true)
    List<String> findRolesByUserId(@Param("userId") UUID userId); */

    @Query(value = """
    SELECT rm.role_name
    FROM user_role_map urm
    JOIN role_master rm ON urm.role_id = rm.role_id
    WHERE urm.user_id = :userId
    """, nativeQuery = true)
    List<String> fetchRoleNamesForJwt(@Param("userId") UUID userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM UserRoleMap urm WHERE urm.userId = :userId")
    void deleteByUserId(@Param("userId") UUID userId);


    // Optional: JPA method to fetch mappings
    List<UserRoleMap> findByUserId(UUID userId);
}
