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

import com.frapee.basic.exceptions.EntityNotFoundException;
import com.frapee.basic.exceptions.GeneralServiceException;
import com.frapee.basic.service.StringService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(path = "/plain_entity")
@RequiredArgsConstructor
@Tag(name = "Plain Entity Example", description = "Plain Entity Example controller API")
/**
 * Plain controller class but using ResponseEntity, the first controller being created
 * will connect with partial service to demostrate usage
  */
public class PlainEntityController {

    @Autowired
    private StringService service;

    @GetMapping(value = "/{id}")
    @Operation(summary = "Read a example item", description = "Read example item by index(id).", operationId="readexample")
    @ApiResponses(value = { 
            @ApiResponse(responseCode = "200", description = "OK"), 
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })    
    /**
     * Retrieve one entry from the list
     * @param id - identifier for the entry, equal to the actual index
     * @return one string which was retrieved from the list
     */
    public ResponseEntity<String> getOne(@Parameter(description = "Index of example", example="1", required=true)
                                          @PathVariable("id") int id) {
        try {
        return ResponseEntity.ok()
            .body(service.getOne(id));
        } catch (IndexOutOfBoundsException ex) {
            throw new EntityNotFoundException();
        }
    }

    @GetMapping
    @Operation(summary = "Read example item list", description = "Read all of the example items", operationId="allexample")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "OK"), 
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })    
    /**
     * Retrieve entire list with no paging informatiom
     * @return the entire list
     */
    public ResponseEntity<List<String>> getAll() {
        return ResponseEntity.ok()
            .body(service.getAll());
    }

    @GetMapping("/search")
    @Operation(summary = "Search for example item list", description = "Pagable search for retrieving paged item lists.", operationId="searchexample")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "OK"), 
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })    
    /**
     * Return part of the list, which is selected by providing paging information, page will start as 0
     * @param page - the actual page number requested
     * @param size - how many entries the page will return
     * @return sublist containing search page
     */
    public ResponseEntity<Page<String>> getSearch(@Parameter(description = "Current Page", example="1") 
                                                   @RequestParam(defaultValue = "0") int page,
                                                  @Parameter(description = "Page size", example="10")
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
    @Operation(summary = "Create example item", description = "Create new item in the list.", operationId="createexample")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "OK"), 
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })    
    /**
     * Create a string to go into the list, do check it string is not null or pre-existing
     * @param input - new string to go into list
     * @return Index number of where string was placed
     */
    public ResponseEntity<Integer> createOne(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Item to create", required = true,
            content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = String.class),
                        examples = @ExampleObject(value = "coconut")))
            @RequestBody String input) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createOne(input));
        } catch (IllegalArgumentException | NullPointerException ex) {
            throw new GeneralServiceException();
        }
    }

    @PutMapping(value = "/{id}")
    @Operation(summary = "Update example item", description = "Update item in the list where index indicate.", operationId="updateexample")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "OK"), 
        @ApiResponse(responseCode = "404", description = "Not found"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })    
    /**
     * Update a string in the list to become something else, it does check if the new string values already exists
     * @param id - identifier for the entry, equal to the actual index
     * @param input - changed value string at index
     * @return General Response Entry with modified value
     */
    public ResponseEntity<String> updateOne(@Parameter(description = "Index of example", example="1", required=true) 
                                             @PathVariable("id") int id, 
                                            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    description = "Item to update", required = true,
                                    content = @Content(mediaType = "application/json",
                                                schema = @Schema(implementation = String.class),
                                                examples = @ExampleObject(value = "coconut")))                             
                                    @Valid @RequestBody String input) {
        try {
            return ResponseEntity.ok()
                .body(service.updateOne(id, input));
        } catch (IndexOutOfBoundsException ex) {
            throw new EntityNotFoundException();
        } catch (IllegalArgumentException | NullPointerException ex) {
            throw new GeneralServiceException();
        }
    }

    @PatchMapping(value = "/{id}")
    @Operation(summary = "Partial update example item", description = "Update item in the list where index indicate (as partial).", operationId="partialupdateexample")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "OK"), 
        @ApiResponse(responseCode = "404", description = "Not found"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })    
    /**
     * Update the string in the list, this is same as update but provide example for the partial will look
     * @param id - identifier for the entry, equal to the actual index
     * @param input - changed value string at index
     * @return General Response Entry with modified value
     */
    public ResponseEntity<String> partialUpdateOne(@Parameter(description = "Index of example", example="1", required=true)
                                                    @PathVariable("id") int id, 
                                                   @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                        description = "Item to partialy update", required = true,
                                                        content = @Content(mediaType = "application/json",
                                                                    schema = @Schema(implementation = String.class),
                                                                    examples = @ExampleObject(value = "coconut")))
                                                        @Valid @RequestBody String input) {
        try {        
            return ResponseEntity.ok()
                .body(service.updateOne(id, input));
        } catch (IndexOutOfBoundsException ex) {
            throw new EntityNotFoundException();
        } catch (IllegalArgumentException | NullPointerException ex) {
            throw new GeneralServiceException();
        }            
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete example item", description = "Delete item in the list where index indicate.", operationId="deleteexample")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "OK"), 
        @ApiResponse(responseCode = "404", description = "Not found"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })    
    /**
     * Remove a string at index from the list
     * @param id - identifier for the entry, equal to the actual index
     * @return General Response Entry containing no Content
     */
    public ResponseEntity<?> deleteOne(@Parameter(description = "Index of example", example="1", required=true)
                                        @PathVariable("id") int id) {
        try {
            service.deleteOne(id);
            return ResponseEntity.noContent()
            .build();
        } catch (IndexOutOfBoundsException ex) {
            throw new EntityNotFoundException();
        }
    }

}
