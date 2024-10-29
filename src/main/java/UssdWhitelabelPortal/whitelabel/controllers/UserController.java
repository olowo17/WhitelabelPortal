package UssdWhitelabelPortal.whitelabel.controllers;


import UssdWhitelabelPortal.whitelabel.entities.PortalUser;
import UssdWhitelabelPortal.whitelabel.exceptions.APIException;
import UssdWhitelabelPortal.whitelabel.params.SignUpRequest;
import UssdWhitelabelPortal.whitelabel.services.PortalUserService;
import UssdWhitelabelPortal.whitelabel.vo.APIResponse;
import UssdWhitelabelPortal.whitelabel.vo.ServiceResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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





