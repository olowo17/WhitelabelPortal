package lazyprogrammer.jwtdemo.controllers;

import lazyprogrammer.jwtdemo.entities.PortalUser;
import lazyprogrammer.jwtdemo.exceptions.APIException;
import lazyprogrammer.jwtdemo.params.SignUpRequest;
import lazyprogrammer.jwtdemo.services.PortalUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/resource")
public class ResourceController {
    private final PortalUserService portalUserService;

    @GetMapping("/secure1")
    public ResponseEntity<String> secureResource(){
        return ResponseEntity.ok("Yes, Your JWT Works...");
    }

    @PostMapping("/secure")
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