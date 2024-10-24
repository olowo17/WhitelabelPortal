package UssdWhitelabelPortal.whitelabel.repositories;

import UssdWhitelabelPortal.whitelabel.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    //Optional<Role> findById(Long roleId);
    List<Role> findByNameIn(List<String> role);



}
