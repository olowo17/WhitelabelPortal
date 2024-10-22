package lazyprogrammer.jwtdemo.dtos;

import lazyprogrammer.jwtdemo.vo.MenuInfoDto;
import lazyprogrammer.jwtdemo.vo.ServiceResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class AuthenticationResponse extends ServiceResponse {

    private LoginData data;

    public AuthenticationResponse(int code) {
        super(code);
    }

    public AuthenticationResponse(int code, String description) {
        super(code, description);
    }


    public LoginData getData(){ return data;}

    public void setData(LoginData data) {
        this.data = data;
    }



    public static class LoginData {

        private String token;
        private PortalUserDto user;
        private List<MenuInfoDto> verticalMenuItems;

        public PortalUserDto getUser() {
            return user;
        }

        public void setUser(PortalUserDto user) {
            this.user = user;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public List<MenuInfoDto> getVerticalMenuItems() {
            return verticalMenuItems;
        }

        public void setVerticalMenuItems(List<MenuInfoDto> verticalMenuItems) {
            this.verticalMenuItems = verticalMenuItems;
        }

    }





}
