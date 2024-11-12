package com.isw.ussd.whitelable.portal.repositories.portal;

import com.isw.ussd.whitelable.portal.entities.portal.PortalUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PortalUserRepository extends JpaRepository<PortalUser, Long> {
    Optional<PortalUser> findByUsername(String username);





}
