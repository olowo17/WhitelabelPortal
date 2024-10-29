package UssdWhitelabelPortal.whitelabel.params;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompleteResetPasswordRequest {

    @NotBlank(message = "Password cannot be null or empty")
    private String password;

    @NotBlank(message = "Password cannot be null or empty")
    private String token;

}