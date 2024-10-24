package UssdWhitelabelPortal.whitelabel.dtos;

import UssdWhitelabelPortal.whitelabel.entities.Branch;
import UssdWhitelabelPortal.whitelabel.entities.Institution;
import UssdWhitelabelPortal.whitelabel.entities.Role;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PortalUserDto {
    private long id;

    private String userName;
    private String password;
    private List<Role> roles;
    private String emailAddress;

    private String mobileNumber;

    private String firstName;

    private String lastName;

    private boolean status;

    private String dateCreated;

    private Date lastPasswordChangeDate;

    private String clientIP;

    private boolean firstLogin;

    private Branch branch;


    private Institution institution;
}
