package UssdWhitelabelPortal.whitelabel.controllers;


import UssdWhitelabelPortal.whitelabel.entities.PortalUser;
import UssdWhitelabelPortal.whitelabel.exceptions.APIException;
import UssdWhitelabelPortal.whitelabel.params.SignUpRequest;
import UssdWhitelabelPortal.whitelabel.services.PortalUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final PortalUserService portalUserService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerPortalUser(@RequestBody SignUpRequest signUpRequest) {
        try {
            PortalUser newUser = portalUserService.registerPortalUser(signUpRequest);
            return ResponseEntity.ok(newUser);
        } catch (APIException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
        }
    }
}





