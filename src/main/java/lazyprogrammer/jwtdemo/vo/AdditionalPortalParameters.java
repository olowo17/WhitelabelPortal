package lazyprogrammer.jwtdemo.vo;
import lazyprogrammer.jwtdemo.dtos.TokenUser;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AdditionalPortalParameters {

    private TokenUser user;
    private String languageCode;
    private String institutionCode;

}
