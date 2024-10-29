package UssdWhitelabelPortal.whitelabel.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    @NotBlank(message = "Email cannot be null or empty")
    private String email;

    @NotBlank(message = "Password cannot be null or empty")
    private String password;
}
