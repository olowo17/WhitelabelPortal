package lazyprogrammer.jwtdemo.repositories;

import lazyprogrammer.jwtdemo.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    //Optional<Role> findById(Long roleId);
    List<Role> findByNameIn(List<String> role);



}
