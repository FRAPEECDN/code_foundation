package com.frapee.securitydemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/api")
@RequiredArgsConstructor
public class ApiController {

    public static final String MESSAGE = "api access works!";

    @GetMapping
    public String get() {
        return MESSAGE;
    }
        
}
