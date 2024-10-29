package com.isw.ussd.whitelable.portal.controllers;

import com.isw.ussd.whitelable.portal.services.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequiredArgsConstructor
@RequestMapping("api/roles")
public class RoleController {

    static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private  RoleService roleService;
    @GetMapping()
    public ResponseEntity<String> loadRoles(){
        logger.info("loadRoles() called");

         roleService.loadRoles();
         return ResponseEntity.ok("SUCCESS");
    }

}
