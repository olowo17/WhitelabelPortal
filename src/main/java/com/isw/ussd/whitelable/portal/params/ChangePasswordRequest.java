package com.isw.ussd.whitelable.portal.params;

import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Password cannot be null or empty")
    private String currentPassword;

    @NotBlank(message = "Password cannot be null or empty")
    private String proposedPassword;

    @NotBlank(message = "Username cannot be null or empty")
    private String userName;

    @NotBlank(message = "Institution Code cannot be null or empty")
    private String institutionCode;

    @NotNull(message = "Auditor ID cannot be null or empty")
    private Long auditorId;
}
