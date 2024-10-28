package com.isw.ussd.whitelable.portal.controllers;


import com.isw.ussd.whitelable.portal.entities.PortalUser;
import com.isw.ussd.whitelable.portal.exceptions.APIException;
import com.isw.ussd.whitelable.portal.params.SignUpRequest;
import com.isw.ussd.whitelable.portal.services.PortalUserService;
import com.isw.ussd.whitelable.portal.vo.APIResponse;
import com.isw.ussd.whitelable.portal.vo.ServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final PortalUserService portalUserService;

    @PostMapping("/signup")
    public APIResponse<?> registerPortalUser(@RequestBody SignUpRequest signUpRequest) {

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





