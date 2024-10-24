package lazyprogrammer.jwtdemo.params;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequest {

    @NotNull
    private String currentPassword;

    @NotNull
    private String proposedPassword;

    private String userName;

    private String institutionCode;

    private Long auditorId;
}
