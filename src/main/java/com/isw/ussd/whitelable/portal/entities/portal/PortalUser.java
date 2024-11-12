package com.isw.ussd.whitelable.portal.entities.portal;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(name = "portal_users")
public class PortalUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private long id;


    @Column(name = "institution_id")
    Long institutionId;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "status")
    private boolean status;


    @Temporal(TemporalType.DATE)
    @Column(name = "create_date")
    private Date createDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "update_date")
    private Date updateDate;

    @Column(name = "create_by")
    private Long createBy;

    @JoinTable(
            name = "portal_users_roles",
            joinColumns = @JoinColumn(name = "portal_user_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id")
    )
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @Column(name = "first_login")
    private boolean firstLogin;

    @Column(name = "checker")
    private boolean checker;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "deleted")
    private boolean deleted;

    @Column(name = "is_super_admin")
    private boolean isSuperAdmin;

}
