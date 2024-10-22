package lazyprogrammer.jwtdemo.controllers;

import lazyprogrammer.jwtdemo.dtos.AuthenticationRequest;
import lazyprogrammer.jwtdemo.dtos.AuthenticationResponse;
import lazyprogrammer.jwtdemo.security.jwt.JwtUtil;
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
            @RequestParam(value = "password") String password,
            @RequestParam(value = "authToken", required = false) String authToken) throws BadCredentialsException {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (BadCredentialsException badCredentialsException) {
            LOG.error("Incorrect username or password");
            throw badCredentialsException;
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        final String token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(token));
    }


}

