package UssdWhitelabelPortal.whitelabel.repositories;

import UssdWhitelabelPortal.whitelabel.entities.PortalUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<PortalUser, Long> {
    Optional<PortalUser> findByUsername(String username);





}
