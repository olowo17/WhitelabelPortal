package lazyprogrammer.jwtdemo.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import lazyprogrammer.jwtdemo.dtos.AuthenticationRequest;
import lazyprogrammer.jwtdemo.dtos.AuthenticationResponse;
import lazyprogrammer.jwtdemo.dtos.CustomUserDetails;
import lazyprogrammer.jwtdemo.dtos.PortalUserDto;
import lazyprogrammer.jwtdemo.entities.Institution;
import lazyprogrammer.jwtdemo.entities.PortalUser;
import lazyprogrammer.jwtdemo.infrastructure.context.Context;
import lazyprogrammer.jwtdemo.mappers.PortalUserMapper;
import lazyprogrammer.jwtdemo.security.jwt.JwtUtil;
import lazyprogrammer.jwtdemo.services.InstitutionService;
import lazyprogrammer.jwtdemo.services.RoleService;
import lazyprogrammer.jwtdemo.utils.LocaleHandler;
import lazyprogrammer.jwtdemo.vo.ExtraData;
import lazyprogrammer.jwtdemo.vo.ResponseMessages;
import lazyprogrammer.jwtdemo.vo.ServiceResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequiredArgsConstructor
@RequestMapping("/mobile-portal/user")
public class AuthController {
    @Autowired
    private  AuthenticationManager authenticationManager;
    @Autowired
    private  JwtUtil jwtUtil;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private InstitutionService institutionService;
    @Autowired
    private RoleService roleService;
    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class.getName());

//    @PostMapping("/authenticate")
//    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws BadCredentialsException {
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
//        } catch (BadCredentialsException badCredentialsException) {
//            LOG.error("Incorrect username or password");
//            throw badCredentialsException;
//        }
//
//        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
//        final String authToken = jwtUtil.generateToken(userDetails);
//
//        return ResponseEntity.ok(new AuthenticationResponse(authToken));
//    }

    
//@PostMapping(value = "/authenticate",
//        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
//        produces = MediaType.APPLICATION_JSON_VALUE)
//public ResponseEntity<AuthenticationResponse> createAuthenticationToken(
//        @RequestParam String username, @RequestParam String password) throws BadCredentialsException {
//    try {
//        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//    } catch (BadCredentialsException badCredentialsException) {
//        LOG.error("Incorrect username or password");
//        throw badCredentialsException;
//    }
//
//    final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//    final String jwt = jwtUtil.generateToken(userDetails);
//
//    return ResponseEntity.ok(new AuthenticationResponse(jwt));
//}

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "password") String password)
            throws BadCredentialsException, JsonProcessingException {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (BadCredentialsException badCredentialsException) {
            LOG.error("Incorrect username or password");
            throw badCredentialsException;
        }

       Context ctx = new Context();
        final CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(email);
        final PortalUser user = userDetails.getUser();
        Institution institution = user.getInstitutionId() != null
                ? institutionService.getInstitutionById(user.getInstitutionId())
                : null;
        PortalUserDto portalUserDto = PortalUserMapper.mapEntityToDTO(user, institution);
        final String token = jwtUtil.generateToken(userDetails);

        AuthenticationResponse loginResponse = new AuthenticationResponse(ServiceResponse.SUCCESS, LocaleHandler.getMessage(ctx.getLanguageCode(), ResponseMessages.GENERAL_SUCCESS_MESSAGE));
        AuthenticationResponse.LoginData data = new AuthenticationResponse.LoginData();

        // Retrieve the first role ID (can be used for UI, permissions, etc.)
        Long firstRoleId = roleService.getFirstRoleId(user);  // Apply getFirstRoleId

        //ExtraData extraData = new ExtraData();

        String details = String.format("Portal user: %s login to support portal", user.getUsername());
        data.setToken(token);
        data.setUser(portalUserDto);
        data.setVerticalMenuItems(roleService.getMenus(ctx, firstRoleId));
        return ResponseEntity.ok(new AuthenticationResponse(data));
    }


}

