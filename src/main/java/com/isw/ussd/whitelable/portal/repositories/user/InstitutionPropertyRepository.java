package com.isw.ussd.whitelable.portal.repositories.user;

import com.isw.ussd.whitelable.portal.entities.user.InstitutionProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstitutionPropertyRepository extends JpaRepository<InstitutionProperty, Long> {
}
