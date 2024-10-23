package lazyprogrammer.jwtdemo.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lazyprogrammer.jwtdemo.dtos.AuthenticationResponse;
import lazyprogrammer.jwtdemo.infrastructure.context.Context;
import lazyprogrammer.jwtdemo.infrastructure.context.ContextService;
import lazyprogrammer.jwtdemo.params.CompleteResetPasswordRequest;
import lazyprogrammer.jwtdemo.params.ResetPasswordRequest;
import lazyprogrammer.jwtdemo.params.ResetPasswordResponse;
import lazyprogrammer.jwtdemo.services.AuthService;
import lazyprogrammer.jwtdemo.vo.APIResponse;
import lazyprogrammer.jwtdemo.vo.ServiceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequiredArgsConstructor
@RequestMapping("/mobile-portal/user")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private ContextService contextService;


    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class.getName());

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "password") String password)
            throws BadCredentialsException, JsonProcessingException {

        Context ctx = contextService.getContextForHttpRequest();
        AuthenticationResponse response = authService.authenticateUser(email, password, ctx);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/password-reset/initiate")
    public APIResponse<?> initiateReset(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
        Context ctx = contextService.getContextForHttpRequest();
        ResetPasswordResponse resetPasswordResponse = authService.initiateResetPassword(ctx, resetPasswordRequest);

        return APIResponse.builder()
                .data(resetPasswordResponse)
                .traceID(ctx.getTraceID())
                .code(ServiceResponse.SUCCESS)
                .description("Password reset successfully initiated")
                .build();
    }

    @PostMapping("/password-reset/complete")
    public APIResponse<?> completeReset(@Valid @RequestBody CompleteResetPasswordRequest completeReset) {
        Context ctx = contextService.getContextForHttpRequest();
        ResetPasswordResponse resetPasswordResponse = authService.completePasswordReset(ctx, completeReset);

        return APIResponse.builder()
                .data(resetPasswordResponse)
                .traceID(ctx.getTraceID())
                .code(ServiceResponse.SUCCESS)
                .description("Password reset complete")
                .build();
    }



}

