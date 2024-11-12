package com.isw.ussd.whitelable.portal.repositories.portal;

import com.isw.ussd.whitelable.portal.entities.portal.RoleFunction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RoleFunctionRepository extends JpaRepository<RoleFunction, Long> {

//    @Query(value = "SELECT r FROM role_functions r WHERE r.role.id = :roleID")
    List<RoleFunction> findByRoleId(Long roleID);

//    @Query(value = "DELETE FROM RoleFunction AS r WHERE r.role.id = :roleID")

    @Modifying
    @Transactional
//    @Query(value = "DELETE FROM role_functions r WHERE role_id = :roleID", nativeQuery = true)
    void deleteByRoleId(Long roleID);

}