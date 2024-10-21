package lazyprogrammer.jwtdemo.dtos;

import lazyprogrammer.jwtdemo.entities.Branch;
import lazyprogrammer.jwtdemo.entities.Institution;
import lombok.Data;

import java.util.Date;

@Data
public class PortalUserDto {
    private long id;

    private String userName;
    private String password;

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
