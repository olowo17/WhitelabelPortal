package com.isw.ussd.whitelable.portal.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.isw.ussd.whitelable.portal.entities.Branch;
import com.isw.ussd.whitelable.portal.entities.Institution;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenUser {
    private long id;

    private String username;

    private Branch branch;

    private String emailAddress;

    private Institution institution;

    private String systemIP;


    public Boolean getIsSuperAdmin() {

        if (institution == null) return true;

        return institution.isISW();

    }
}
