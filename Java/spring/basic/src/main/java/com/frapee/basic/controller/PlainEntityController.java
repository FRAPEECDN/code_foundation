package com.frapee.basic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import com.frapee.basic.exceptions.GeneralServiceException;
import com.frapee.basic.service.StringService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(path = "/plain")
@RequiredArgsConstructor
/**
 * Plain controller class, the first controller being created
 * will connect with partial service to demostrate usage
 * Also it will always return ResponseEntity types
 */
public class PlainEntityController {

    @Autowired
    private StringService service;

    @GetMapping(value = "/{id}")
    /**
     * Retrieve one entry from the list
     * @param id - identifier for the entry, equal to the actual index
     * @return one string which was retrieved from the list
     */
    public ResponseEntity<String> getOne(@PathVariable("id") int id) {
        try {
        return ResponseEntity.ok()
            .body(service.getOne(id));
        } catch (IndexOutOfBoundsException ex) {
            throw new ResourceNotFoundException("Out of bounds");
        }
    }

    @GetMapping
    /**
     * Retrieve entire list with no paging informatiom
     * @return the entire list
     */
    public ResponseEntity<List<String>> getAll() {
        return ResponseEntity.ok()
            .body(service.getAll());
    }

    @GetMapping("/search")
    /**
     * Return part of the list, which is selected by providing paging information, page will start as 0
     * @param page - the actual page number requested
     * @param size - how many entries the page will return
     * @return sublist containing search page
     */
    public ResponseEntity<Page<String>> getSearch(@RequestParam(defaultValue = "0") int page, 
                    @RequestParam(defaultValue = "10") int size) {
        List<String> returned = service.getAll();

        int totalSize = returned.size();
        int startIndex = page * size;
        int endIndex = Math.min(startIndex + size, totalSize); 

        List<String> pageContent = returned.subList(startIndex, endIndex);
        Page<String> body = new PageImpl<>(pageContent, PageRequest.of(page, size), totalSize);
        return ResponseEntity.ok()
            .body(body);
    }

    @PostMapping
    /**
     * Create a string to go into the list, do check it string is not null or pre-existing
     * @param input - new string to go into list
     * @return Index number of where string was placed
     */
    public ResponseEntity<Integer> createOne(@RequestBody String input) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createOne(input));
        } catch (IllegalArgumentException | NullPointerException ex) {
            throw new GeneralServiceException();
        }
    }

    @PutMapping(value = "/{id}")
    /**
     * Update a string in the list to become something else, it does check if the new string values already exists
     * @param id - identifier for the entry, equal to the actual index
     * @param input - changed value string at index
     * @return General Response Entry with modified value
     */
    public ResponseEntity<String> updateOne(@PathVariable("id") int id, @Valid @RequestBody String input) {
        try {
            return ResponseEntity.ok()
                .body(service.updateOne(id, input));
        } catch (IndexOutOfBoundsException ex) {
            throw new ResourceNotFoundException("Out of bounds");
        } catch (IllegalArgumentException | NullPointerException ex) {
            throw new GeneralServiceException();
        }
    }

    @PatchMapping(value = "/{id}")
    /**
     * Update the string in the list, this is same as update but provide example for the partial will look
     * @param id - identifier for the entry, equal to the actual index
     * @param input - changed value string at index
     * @return General Response Entry with modified value
     */
    public ResponseEntity<String> partialUpdateOne(@PathVariable("id") int id, @Valid @RequestBody String input) {
        try {        
            return ResponseEntity.ok()
                .body(service.updateOne(id, input));
        } catch (IndexOutOfBoundsException ex) {
            throw new ResourceNotFoundException("Out of bounds");
        } catch (IllegalArgumentException | NullPointerException ex) {
            throw new GeneralServiceException();
        }            
    }

    @DeleteMapping(value = "/{id}")
    /**
     * Remove a string at index from the list
     * @param id - identifier for the entry, equal to the actual index
     * @return General Response Entry containing no Content
     */
    public ResponseEntity<?> deleteOne(@PathVariable("id") int id) {
        try {
            service.deleteOne(id);
            return ResponseEntity.noContent()
            .build();
        } catch (IndexOutOfBoundsException ex) {
            throw new ResourceNotFoundException("Out of bounds");
        }
    }

}
