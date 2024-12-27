package com.frapee.securitydemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/admin")
@RequiredArgsConstructor
public class AdminController {

    public static final String MESSAGE = "admin access works!";

    @GetMapping
    @RolesAllowed("ROLE_ADMIN")
    public String get() {
        return MESSAGE;
    }
    
}
