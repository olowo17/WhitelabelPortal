package com.isw.ussd.whitelable.portal.params;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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

    @NotNull(message = "First name cannot be null or empty")
    private String firstName;

    @NotNull(message = "Last name cannot be null or empty")
    private String lastName;

    @NotNull(message = "Email cannot be null or empty")
    private String email;

    @NotNull(message = "Password cannot be null or empty")
    private String password;

    @NotNull(message = "Role ID cannot be null or empty")
    private Long roleId;

    @NotNull(message = "Mobile number cannot be null or empty")
    @Pattern(regexp = "^\\d{11,14}$", message = "Invalid mobile number format")
    private String mobileNumber;

    @NotNull(message = "Institution code cannot be null or empty")
    private String institutionCode;

    private Boolean isBranchAuthorizer = false;

    private Boolean isChecker = false;

    @NotNull(message = "Branch code cannot be null or empty")
    private String branchCode;

    @NotNull(message = "Auditor ID cannot be null or empty")
    private Long auditorId;

    private List<String> roles;
}
