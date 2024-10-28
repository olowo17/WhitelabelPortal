package com.isw.ussd.whitelable.portal.repositories;

import com.isw.ussd.whitelable.portal.entities.RoleFunction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RoleFunctionRepository extends JpaRepository<RoleFunction, Long> {

    @Query(value = "SELECT r FROM RoleFunction r WHERE r.role.id = :roleID")
    List<RoleFunction> findByRole(@Param("roleID") long roleID);

//    @Query(value = "DELETE FROM RoleFunction AS r WHERE r.role.id = :roleID")

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM RoleFunctions WHERE role_id = :roleID", nativeQuery = true)
    void deleteByRoleId(
            @Param("roleID") Long roleID
    );

}