package UssdWhitelabelPortal.whitelabel.entities;

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
    private long id;
    Long institutionId;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private boolean status;
    @Temporal(TemporalType.DATE)
    private Date createDate;
    @Temporal(TemporalType.DATE)
    private Date updateDate;
    private Long createBy;
    //@JoinColumn(name = "role_id", nullable = false)
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "branch_id")
    private Branch branch;
    private boolean firstLogin;
    private boolean checker;
    private String mobileNumber;
    private boolean deleted;
    private boolean isSuperAdmin;

}
