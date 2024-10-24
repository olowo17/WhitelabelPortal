package UssdWhitelabelPortal.whitelabel.vo;
import UssdWhitelabelPortal.whitelabel.dtos.TokenUser;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AdditionalPortalParameters {

    private TokenUser user;
    private String languageCode;
    private String institutionCode;

}
