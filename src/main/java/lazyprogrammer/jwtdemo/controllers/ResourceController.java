package lazyprogrammer.jwtdemo.controllers;

import lazyprogrammer.jwtdemo.entities.PortalUser;
import lazyprogrammer.jwtdemo.exceptions.APIException;
import lazyprogrammer.jwtdemo.params.SignUpRequest;
import lazyprogrammer.jwtdemo.services.PortalUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/resource")
public class ResourceController {

    @GetMapping("/secure")
    public ResponseEntity<String> secureResource(){
        return ResponseEntity.ok("Yes, Your JWT Works...");
    }

}