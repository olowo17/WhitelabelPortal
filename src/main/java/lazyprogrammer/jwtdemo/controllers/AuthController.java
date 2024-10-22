package lazyprogrammer.jwtdemo.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import lazyprogrammer.jwtdemo.dtos.AuthenticationResponse;
import lazyprogrammer.jwtdemo.dtos.CustomUserDetails;
import lazyprogrammer.jwtdemo.dtos.PortalUserDto;
import lazyprogrammer.jwtdemo.entities.Institution;
import lazyprogrammer.jwtdemo.entities.PortalUser;
import lazyprogrammer.jwtdemo.infrastructure.context.Context;
import lazyprogrammer.jwtdemo.infrastructure.context.ContextService;
import lazyprogrammer.jwtdemo.mappers.PortalUserMapper;
import lazyprogrammer.jwtdemo.security.jwt.JwtUtil;
import lazyprogrammer.jwtdemo.services.AuthService;
import lazyprogrammer.jwtdemo.services.InstitutionService;
import lazyprogrammer.jwtdemo.services.RoleService;
import lazyprogrammer.jwtdemo.utils.LocaleHandler;
import lazyprogrammer.jwtdemo.vo.ResponseMessages;
import lazyprogrammer.jwtdemo.vo.ServiceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
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


}

