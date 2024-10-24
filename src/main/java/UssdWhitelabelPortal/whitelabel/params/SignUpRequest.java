package UssdWhitelabelPortal.whitelabel.params;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private Long roleId;

    @NotNull
    private String mobileNumber;

    private String institutionCode;

    private Boolean isBranchAuthorizer = false;

    private Boolean isChecker = false;

    @NotNull
    private String branchCode;

    private Long auditorId;
    private List<String> roles;
}
