package com.isw.ussd.whitelable.portal.params;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompleteResetPasswordRequest {

    @NotNull(message = "Password cannot be null or empty")
    private String password;

    @NotNull(message = "Password cannot be null or empty")
    private String token;

}