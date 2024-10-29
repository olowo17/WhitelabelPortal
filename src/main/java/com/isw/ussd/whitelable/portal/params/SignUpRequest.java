package com.isw.ussd.whitelable.portal.params;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "First name cannot be null or empty")
    private String firstName;

    @NotBlank(message = "Last name cannot be null or empty")
    private String lastName;

    @NotBlank(message = "Email cannot be null or empty")
//    @Pattern(regexp = "^[a-z0-9]+@(.+)$\n", message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password cannot be null or empty")
    private String password;

    @NotNull(message = "Role ID cannot be null or empty")
    private Long roleId;

    @NotBlank(message = "Mobile number cannot be null or empty")
    @Pattern(regexp = "^\\d{10,14}$", message = "Invalid mobile number format")
    private String mobileNumber;

    @NotBlank(message = "Institution code cannot be null or empty")
    private String institutionCode;

    private Boolean isBranchAuthorizer = false;

    private Boolean isChecker = false;

    @NotBlank(message = "Branch code cannot be null or empty")
    private String branchCode;

    @NotNull(message = "Auditor ID cannot be null or empty")
    private Long auditorId;

    @NotNull(message = "Roles cannot be null")
    @Size(min = 1, message = "There must be at least one role")
    private List<@NotBlank(message = "Role cannot be blank") String> roles;
}
