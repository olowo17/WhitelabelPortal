package UssdWhitelabelPortal.whitelabel.controllers;

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
    @PostMapping("/change-password")
    public APIResponse<?> changePassword(
            HttpServletRequest request,
            @Valid @RequestBody ChangePasswordRequest changeRequest
    ) {
        TokenUser user = null;
        try {
            user = jwtUtil.getTokenUserFromRequest(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println(user);
        System.out.println(user.getId());

        Context ctx = contextService.getContextForHttpRequest();

        changeRequest.setAuditorId(user.getId());
        if (!user.getIsSuperAdmin()) {
            changeRequest.setInstitutionCode(user.getInstitution().getCode());
        }

        if (StringUtils.isBlank(changeRequest.getUserName())) {
            changeRequest.setUserName(user.getUsername());
        }

        ResetPasswordResponse resetPasswordResponse = authService.changePassword(ctx, changeRequest);
        return APIResponse.builder()
                .data(resetPasswordResponse)
                .traceID(ctx.getTraceID())
                .code(ServiceResponse.SUCCESS)
                .description("Change password successful")
                .build();
    }

}

