package com.isw.ussd.whitelable.portal.controllers;


import com.isw.ussd.whitelable.portal.entities.PortalUser;
import com.isw.ussd.whitelable.portal.exceptions.APIException;
import com.isw.ussd.whitelable.portal.params.SignUpRequest;
import com.isw.ussd.whitelable.portal.services.PortalUserService;
import com.isw.ussd.whitelable.portal.vo.APIResponse;
import com.isw.ussd.whitelable.portal.vo.ServiceResponse;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final PortalUserService portalUserService;

    @PostMapping("/signup")
    public APIResponse<?> registerPortalUser(@Valid @RequestBody SignUpRequest signUpRequest, BindingResult bindingResult) {

        // Check for validation errors
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.joining(", "));

            // Return API response with validation error message
            return APIResponse.builder()
                    .data(null)
                    .code(ServiceResponse.ERROR)
                    .description("Validation error(s): " + errorMessage)
                    .build();
        }

        PortalUser newUser;
        try {
            newUser = portalUserService.registerPortalUser(signUpRequest);
        } catch (APIException e) {
            return APIResponse.builder()
                    .data(null)
                    .code(ServiceResponse.ERROR)
                    .description("Error occurred during signup: " + e.getMessage())
                    .build();
        } catch (Exception e) {
            return APIResponse.builder()
                    .data(null)
                    .code(ServiceResponse.ERROR)
                    .description("An unexpected error occurred: " + e.getMessage())
                    .build();
        }
        return APIResponse.builder()
                .data(newUser)
                .code(ServiceResponse.SUCCESS)
                .description("ADMIN added successfully")
                .build();
    }
}





