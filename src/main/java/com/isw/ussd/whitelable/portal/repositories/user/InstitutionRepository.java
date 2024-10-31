package com.isw.ussd.whitelable.portal.repositories.user;

import com.isw.ussd.whitelable.portal.entities.user.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Long> {

    Optional<Institution> findById(Long Long);

    Optional<Institution> findByCode(String code);
}
