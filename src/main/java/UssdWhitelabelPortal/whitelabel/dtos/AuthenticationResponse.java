package UssdWhitelabelPortal.whitelabel.dtos;

import UssdWhitelabelPortal.whitelabel.vo.MenuInfoDto;
import UssdWhitelabelPortal.whitelabel.vo.ServiceResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class AuthenticationResponse extends ServiceResponse {

    private LoginData data;

    public AuthenticationResponse(int code) {
        super(code);
    }

    public AuthenticationResponse(int code, String description) {
        super(code, description);
    }


    @Setter
    @Getter
    public static class LoginData {

        private String token;
        private PortalUserDto user;
        private List<MenuInfoDto> verticalMenuItems;

    }





}
