package UssdWhitelabelPortal.whitelabel.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "RoleFunctions")
@NamedQueries({@NamedQuery(name = "RoleFunction.findAll", query = "SELECT r FROM RoleFunction r "),
        @NamedQuery(name = "RoleFunction.findByRole", query = "SELECT r FROM RoleFunction r WHERE r.role.id = :roleID"),
        @NamedQuery(name = "RoleFunction.deleteByRole", query = "DELETE FROM RoleFunction r WHERE r.role.id = :roleID")
})
public class RoleFunction  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Menu menu;
    @ManyToOne
    private Role role;


}