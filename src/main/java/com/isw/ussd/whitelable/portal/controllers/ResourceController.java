package com.isw.ussd.whitelable.portal.controllers;

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