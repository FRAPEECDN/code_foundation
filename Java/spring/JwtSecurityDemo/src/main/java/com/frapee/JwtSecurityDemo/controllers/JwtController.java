package com.frapee.JwtSecurityDemo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.userdetails.User;

import com.frapee.JwtSecurityDemo.jwt.JwtResponse;
import com.frapee.JwtSecurityDemo.jwt.JwtService;
import com.frapee.JwtSecurityDemo.jwt.LoginRequest;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;

@RestController
public class JwtController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtService service;

    @PostMapping("/signin")
    @SecurityRequirements()
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.name(), loginRequest.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        User userDetails = (User) authentication.getPrincipal();
        String jwt = service.generateJwtToken(authentication);
        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername()));

    }    

}
