package com.isw.ussd.whitelable.portal.repositories.portal;

import com.isw.ussd.whitelable.portal.entities.portal.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch,Long> {
    Optional<Branch> findByCode(String code);
}
