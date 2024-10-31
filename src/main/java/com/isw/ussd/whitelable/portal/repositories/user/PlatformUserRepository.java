package com.isw.ussd.whitelable.portal.repositories.user;

import com.isw.ussd.whitelable.portal.entities.user.PlatformUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlatformUserRepository extends JpaRepository<PlatformUser, Long> {
}
