package com.frapee.basic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.frapee.basic.dto.SimpleDto;
import com.frapee.basic.service.SimpleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/simple")
@RequiredArgsConstructor
/**
 * Simple Controller allowing access to the Simple CRUD operations
 */
public class SimpleController {
    
    @Autowired
    private SimpleService service;

    @GetMapping(value = "/{id}")
    /**
     * Retrieve one entry from the list
     * @param id - identifier for the entry, equal to the actual index
     * @return one string which was retrieved from the list
     */
    public SimpleDto getOne(@PathVariable("id") int id) {
        return service.getOne(id);
    }

    @GetMapping
    /**
     * Retrieve entire list with no paging informatiom
     * @return the entire list
     */
    public List<SimpleDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/search")
    /**
     * Return part of the list, which is selected by providing paging information, page will start as 0
     * @param page - the actual page number requested
     * @param size - how many entries the page will return
     * @return sublist containing search page
     */
    public Page<SimpleDto> getSearch(@RequestParam(defaultValue = "0") int page, 
                    @RequestParam(defaultValue = "10") int size) {
        List<SimpleDto> returned = service.getAll();

        int totalSize = returned.size();
        int startIndex = page * size;
        int endIndex = Math.min(startIndex + size, totalSize); 

        List<SimpleDto> pageContent = returned.subList(startIndex, endIndex);
        return new PageImpl<>(pageContent, PageRequest.of(page, size), totalSize);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    /**
     * Create a string to go into the list, do check it string is not null or pre-existing
     * @param input - new string to go into list
     * @return Index number of where string was placed
     */
    public SimpleDto createOne(@RequestBody @Valid SimpleDto input) {
        return service.createOne(input);
    }

    @PutMapping(value = "/{id}")
    /**
     * Update a string in the list to become something else, it does check if the new string values already exists
     * @param id - identifier for the entry, equal to the actual index
     * @param input - changed value string at index
     * @return General Response Entry with modified value
     */
    public SimpleDto updateOne(@PathVariable("id") int id, @Valid @RequestBody SimpleDto input) {
        return service.updateOne(id, input);
    }

    @PatchMapping(value = "/{id}")
    /**
     * Update the string in the list, this is same as update but provide example for the partial will look
     * @param id - identifier for the entry, equal to the actual index
     * @param input - changed value string at index
     * @return General Response Entry with modified value
     */
    public SimpleDto partialUpdateOne(@PathVariable("id") int id, @Valid @RequestBody SimpleDto input) {
        return service.updateOne(id, input);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    /**
     * Remove a string at index from the list
     * @param id - identifier for the entry, equal to the actual index
     * @return General Response Entry containing no Content
     */
    public void deleteOne(@PathVariable("id") int id) {
        service.deleteOne(id);
    }

}
