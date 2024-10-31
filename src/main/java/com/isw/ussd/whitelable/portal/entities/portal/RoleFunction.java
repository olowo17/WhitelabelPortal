package com.isw.ussd.whitelable.portal.entities.portal;

import com.isw.ussd.whitelable.portal.entities.portal.Menu;
import com.isw.ussd.whitelable.portal.entities.portal.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "role_functions")
//@NamedQueries({@NamedQuery(name = "RoleFunction.findAll", query = "SELECT r FROM RoleFunction r "),
//        @NamedQuery(name = "RoleFunction.findByRole", query = "SELECT r FROM RoleFunction r WHERE r.role.id = :roleID"),
//        @NamedQuery(name = "RoleFunction.deleteByRole", query = "DELETE FROM RoleFunction r WHERE r.role.id = :roleID")
//})
public class RoleFunction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;


}