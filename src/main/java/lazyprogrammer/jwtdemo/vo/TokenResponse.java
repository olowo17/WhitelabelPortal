package lazyprogrammer.jwtdemo.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse extends ServiceResponse {

    private String sessionID;

    private Integer sessionIDExpirySecs;

    private String sessionExpiryTime;

    public TokenResponse(int code) {
        super(code);
    }

    public TokenResponse(int code, String description) {
        super(code, description);
    }

}
