package lazyprogrammer.jwtdemo.controllers;


import lazyprogrammer.jwtdemo.dtos.PortalUserDto;
import lazyprogrammer.jwtdemo.entities.PortalUser;
import lazyprogrammer.jwtdemo.exceptions.APIException;
import lazyprogrammer.jwtdemo.exceptions.InvalidPayloadException;
import lazyprogrammer.jwtdemo.exceptions.UserIdAlreadyExistException;
import lazyprogrammer.jwtdemo.params.SignUpRequest;
import lazyprogrammer.jwtdemo.services.PortalUserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final PortalUserService portalUserService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerAdmin(@RequestBody SignUpRequest signUpRequest) {
        try {
            PortalUser newUser = portalUserService.registerAdmin(signUpRequest);
            return ResponseEntity.ok(newUser);
        } catch (APIException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
        }
    }
}



//    @PostMapping("/register")
//    public ResponseEntity<PortalUserDto> saveUser(@RequestBody SignUpRequest signUpRequest) {
//        if (Objects.isNull(signUpRequest)) {
//            throw new InvalidPayloadException("Payload cannot be Null");
//        }
//        if(userService.registerAdmin(signUpRequest.getEmail())){
//            throw new UserIdAlreadyExistException("Username is already taken");
//        }
//        return userService.saveUser(portalUserDto);
//    }


