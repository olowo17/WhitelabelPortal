package lazyprogrammer.jwtdemo.entities;

import jakarta.persistence.*;

import java.io.Serializable;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


}