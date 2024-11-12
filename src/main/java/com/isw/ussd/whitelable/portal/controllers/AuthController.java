package com.isw.ussd.whitelable.portal.controllers;

import com.isw.ussd.whitelable.portal.exceptions.APIException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.isw.ussd.whitelable.portal.utils.ValidationUtil;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import com.isw.ussd.whitelable.portal.dtos.AuthenticationResponse;
import com.isw.ussd.whitelable.portal.dtos.TokenUser;
import com.isw.ussd.whitelable.portal.infrastructure.context.Context;
import com.isw.ussd.whitelable.portal.infrastructure.context.ContextService;
import com.isw.ussd.whitelable.portal.params.ChangePasswordRequest;
import com.isw.ussd.whitelable.portal.params.CompleteResetPasswordRequest;
import com.isw.ussd.whitelable.portal.params.ResetPasswordRequest;
import com.isw.ussd.whitelable.portal.params.ResetPasswordResponse;
import com.isw.ussd.whitelable.portal.security.jwt.JwtUtil;
import com.isw.ussd.whitelable.portal.services.AuthService;
import com.isw.ussd.whitelable.portal.vo.APIResponse;
import com.isw.ussd.whitelable.portal.vo.ServiceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

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
    @Autowired
    private HttpServletRequest request;

    static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestParam(value = "email") String email,
                                                                            @RequestParam(value = "password") String password) throws BadCredentialsException, JsonProcessingException {
        logger.info("URL called: {}", request.getRequestURL());

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

    @PostMapping("/password-reset/initate")
    public APIResponse<?> initiateReset(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest, BindingResult bindingResult) {
        logger.info("URL called: {}", request.getRequestURL());

        if (bindingResult.hasErrors()) {
            return ValidationUtil.generateErrorResponse(bindingResult);
        }

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
                    .description("Unexpected error occurred: " + e.getMessage())
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
    public APIResponse<?> completeReset(@Valid @RequestBody CompleteResetPasswordRequest completeReset, BindingResult bindingResult) {
        logger.info("URL called: {}", request.getRequestURL());;

        if (bindingResult.hasErrors()) {
            return ValidationUtil.generateErrorResponse(bindingResult);
        }

        Context ctx = contextService.getContextForHttpRequest();
        ResetPasswordResponse resetPasswordResponse = null;

        try {
            resetPasswordResponse = authService.completePasswordReset(ctx, completeReset);

        } catch (APIException e) {

            return APIResponse.builder()
                    .code(ServiceResponse.ERROR)
                    .traceID(ctx.getTraceID())
                    .description("Password reset failed: " + e.getMessage())
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
                .description("Password reset complete")
                .build();
    }

    @PostMapping("/change-password")
    public APIResponse<?> changePassword(HttpServletRequest request, @Valid @RequestBody ChangePasswordRequest changeRequest, BindingResult bindingResult) {
        logger.info("URL called: {}", request.getRequestURL());

        if (bindingResult.hasErrors()) {
            return ValidationUtil.generateErrorResponse(bindingResult);
        }

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
                    .description("Unexpected error occurred: " + e.getMessage())
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

