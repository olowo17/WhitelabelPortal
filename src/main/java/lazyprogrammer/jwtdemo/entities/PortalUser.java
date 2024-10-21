package lazyprogrammer.jwtdemo.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;



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
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    @ManyToOne
    private Role role;
    @ManyToOne
    private Branch branch;
    private boolean firstLogin;
    private boolean checker;
    private String mobileNumber;
    private boolean deleted;
    private boolean isSuperAdmin;

}
