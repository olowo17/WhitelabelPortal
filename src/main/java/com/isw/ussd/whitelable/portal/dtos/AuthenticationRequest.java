package com.isw.ussd.whitelable.portal.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    @NotNull(message = "Email cannot be null or empty")
    private String email;

    @NotNull(message = "Password cannot be null or empty")
    private String password;
}
