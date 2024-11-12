package com.isw.ussd.whitelable.portal.dtos;

import com.isw.ussd.whitelable.portal.entities.portal.Branch;
import com.isw.ussd.whitelable.portal.entities.user.Institution;
import com.isw.ussd.whitelable.portal.entities.portal.Role;
import com.isw.ussd.whitelable.portal.entities.user.Institution;
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
