package UssdWhitelabelPortal.whitelabel.controllers;

import UssdWhitelabelPortal.whitelabel.exceptions.APIException;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import UssdWhitelabelPortal.whitelabel.dtos.AuthenticationResponse;
import UssdWhitelabelPortal.whitelabel.dtos.TokenUser;
import UssdWhitelabelPortal.whitelabel.infrastructure.context.Context;
import UssdWhitelabelPortal.whitelabel.infrastructure.context.ContextService;
import UssdWhitelabelPortal.whitelabel.params.ChangePasswordRequest;
import UssdWhitelabelPortal.whitelabel.params.CompleteResetPasswordRequest;
import UssdWhitelabelPortal.whitelabel.params.ResetPasswordRequest;
import UssdWhitelabelPortal.whitelabel.params.ResetPasswordResponse;
import UssdWhitelabelPortal.whitelabel.security.jwt.JwtUtil;
import UssdWhitelabelPortal.whitelabel.services.AuthService;
import UssdWhitelabelPortal.whitelabel.vo.APIResponse;
import UssdWhitelabelPortal.whitelabel.vo.ServiceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @Autowired
    private JwtUtil jwtUtil;


    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class.getName());

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "password") String password)
            throws BadCredentialsException, JsonProcessingException {

        Context ctx = contextService.getContextForHttpRequest();
        AuthenticationResponse response = null;
        try {

            response = authService.authenticateUser(email, password, ctx);
        } catch (APIException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthenticationResponse(403, "Authentication failed: " + e.getMessage()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthenticationResponse(401, "Invalid email or password."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AuthenticationResponse(500, "An unexpected error occurred: " + e.getMessage()));
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/password-reset/initiate")
    public APIResponse<?> initiateReset(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
        Context ctx = contextService.getContextForHttpRequest();
        ResetPasswordResponse resetPasswordResponse = null;

        try {

            resetPasswordResponse = authService.initiateResetPassword(ctx, resetPasswordRequest);
        } catch (APIException e) {

            return APIResponse.builder()
                    .code(ServiceResponse.ERROR)
                    .traceID(ctx.getTraceID())
                    .description("Password reset initiation failed: " + e.getMessage())
                    .statusCode(HttpStatus.NOT_FOUND)
                    .build();
        } catch (IllegalArgumentException e) {

            return APIResponse.builder()
                    .code(ServiceResponse.ERROR)
                    .traceID(ctx.getTraceID())
                    .description("Invalid input: " + e.getMessage())
                    .statusCode(HttpStatus.NOT_FOUND)
                    .build();
        } catch (Exception e) {

            return APIResponse.builder()
                    .code(ServiceResponse.ERROR)
                    .traceID(ctx.getTraceID())
                    .description("An unexpected error occurred: " + e.getMessage())
                    .statusCode(HttpStatus.NOT_FOUND)
                    .build();
        }

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
        ResetPasswordResponse resetPasswordResponse = null;

        try {
            // Attempt to complete the password reset process
            resetPasswordResponse = authService.completePasswordReset(ctx, completeReset);

        } catch (APIException e) {
            // Handle business logic or validation errors
            return APIResponse.builder()
                    .code(ServiceResponse.ERROR)
                    .traceID(ctx.getTraceID())
                    .description("Password reset failed: " + e.getMessage())
                    .statusCode(HttpStatus.NOT_FOUND)
                    .build();

        } catch (IllegalArgumentException e) {
            // Handle errors related to invalid input (e.g., missing parameters)
            return APIResponse.builder()
                    .code(ServiceResponse.ERROR)
                    .traceID(ctx.getTraceID())
                    .description("Invalid input: " + e.getMessage())
                    .statusCode(HttpStatus.NOT_FOUND)
                    .build();

        } catch (Exception e) {
            // Handle any unexpected errors
            return APIResponse.builder()
                    .code(ServiceResponse.ERROR)
                    .traceID(ctx.getTraceID())
                    .description("An unexpected error occurred: " + e.getMessage())
                    .statusCode(HttpStatus.NOT_FOUND)
                    .build();
        }

        // If successful, return the success response
        return APIResponse.builder()
                .data(resetPasswordResponse)
                .traceID(ctx.getTraceID())
                .code(ServiceResponse.SUCCESS)
                .description("Password reset complete")
                .build();
    }

    @PostMapping("/change-password")
    public APIResponse<?> changePassword(HttpServletRequest request, @Valid @RequestBody ChangePasswordRequest changeRequest) {

        TokenUser user = null;
        Context ctx = contextService.getContextForHttpRequest();
        ResetPasswordResponse resetPasswordResponse = null;

        try {
            user = jwtUtil.getTokenUserFromRequest(request);

            changeRequest.setAuditorId(user.getId());

            if (!user.getIsSuperAdmin()) {
                changeRequest.setInstitutionCode(user.getInstitution().getCode());
            }

            if (StringUtils.isBlank(changeRequest.getUserName())) {
                changeRequest.setUserName(user.getUsername());
            }

            resetPasswordResponse = authService.changePassword(ctx, changeRequest);

        } catch (APIException e) {

            return APIResponse.builder()
                    .code(ServiceResponse.ERROR)
                    .traceID(ctx.getTraceID())
                    .description("Password change failed: " + e.getMessage())
                    .build();

        } catch (IllegalArgumentException e) {

            return APIResponse.builder()
                    .code(ServiceResponse.ERROR)
                    .traceID(ctx.getTraceID())
                    .description("Invalid input: " + e.getMessage())
                    .build();

        } catch (Exception e) {

            return APIResponse.builder()
                    .code(ServiceResponse.ERROR)
                    .traceID(ctx.getTraceID())
                    .description("An unexpected error occurred: " + e.getMessage())
                    .build();
        }

        return APIResponse.builder()
                .data(resetPasswordResponse)
                .traceID(ctx.getTraceID())
                .code(ServiceResponse.SUCCESS)
                .description("Password change successful")
                .build();
    }
}

