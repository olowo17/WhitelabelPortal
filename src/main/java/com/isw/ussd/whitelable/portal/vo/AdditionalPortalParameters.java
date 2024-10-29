package com.isw.ussd.whitelable.portal.vo;
import com.isw.ussd.whitelable.portal.dtos.TokenUser;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AdditionalPortalParameters {

    private TokenUser user;
    private String languageCode;
    private String institutionCode;

}
