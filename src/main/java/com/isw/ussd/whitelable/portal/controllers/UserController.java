package com.isw.ussd.whitelable.portal.controllers;


import com.isw.ussd.whitelable.portal.entities.PortalUser;
import com.isw.ussd.whitelable.portal.exceptions.APIException;
import com.isw.ussd.whitelable.portal.params.SignUpRequest;
import com.isw.ussd.whitelable.portal.services.PortalUserService;
import com.isw.ussd.whitelable.portal.vo.APIResponse;
import com.isw.ussd.whitelable.portal.vo.ServiceResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final PortalUserService portalUserService;

    private final HttpServletRequest request;

    static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @PostMapping("/signup")
    public APIResponse<?> registerPortalUser(@Valid @RequestBody SignUpRequest signUpRequest, BindingResult bindingResult) {
        logger.info("URL called: {}", request.getRequestURL());

        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.joining(", "+'\''));

            return APIResponse.builder()
                    .data(null)
                    .code(ServiceResponse.ERROR)
                    .description(errorMessage)
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
                    .description("Unexpected error occurred: " + e.getMessage())
                    .build();
        }
        return APIResponse.builder()
                .data(newUser)
                .code(ServiceResponse.SUCCESS)
                .description(newUser.getRoles() + " added successfully")
                .build();
    }
}





