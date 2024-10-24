package UssdWhitelabelPortal.whitelabel.controllers;

import UssdWhitelabelPortal.whitelabel.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequiredArgsConstructor
@RequestMapping("api/roles")
public class RoleController {

    @Autowired
    private  RoleService roleService;
    @GetMapping()
    public ResponseEntity<String> loadRoles(){
         roleService.loadRoles();
         return ResponseEntity.ok("SUCCESS");
    }

}
