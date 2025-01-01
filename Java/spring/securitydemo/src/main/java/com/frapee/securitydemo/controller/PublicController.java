package com.frapee.securitydemo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/public")
@RequiredArgsConstructor
public class PublicController {

    public static final String MESSAGE = "public access works!";
    public static final String POSTED = "public post works!";
    public static final String PUT = "public put works!";

    @GetMapping
    public String get() {
        return MESSAGE;
    }
        
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String post(@RequestBody String entity) {
        return POSTED;
    }

    @PutMapping(value="/{id}")
    public String putMethodName(@PathVariable String id, @RequestBody String entity) {
        return PUT;
    }

    @DeleteMapping(value="/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {

    }   

}
