package com.isw.ussd.whitelable.portal.dtos;

import com.isw.ussd.whitelable.portal.entities.Branch;
import com.isw.ussd.whitelable.portal.entities.Institution;
import com.isw.ussd.whitelable.portal.entities.Role;
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
