package lazyprogrammer.jwtdemo.repositories;

import lazyprogrammer.jwtdemo.entities.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Long> {

    Optional<Institution> findById(Long Long);

    Optional<Institution> findByCode(String code);
}
