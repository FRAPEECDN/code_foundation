package com.frapee.securitydemo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping(path = "/admin")
@RequiredArgsConstructor
@OpenAPIDefinition(info = @Info(title = "Security API", version = "v1"))
@SecurityRequirement(name = "basicAuth")
public class AdminController {

    public static final String MESSAGE = "admin access works!";
    public static final String POSTED = "admin post works!";
    public static final String PUT = "admin put works!";

    @GetMapping
    @RolesAllowed("ROLE_ADMIN")
    public String get() {
        return MESSAGE;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @RolesAllowed("ROLE_ADMIN")
    public String post(@RequestBody String entity) {
        return POSTED;
    }
    
    @PutMapping(value="/{id}")
    @RolesAllowed("ROLE_ADMIN")
    public String putMethodName(@PathVariable String id, @RequestBody String entity) {
        return PUT;
    }

    @DeleteMapping(value="/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RolesAllowed("ROLE_ADMIN")
    public void delete(@PathVariable String id) {

    }

}
