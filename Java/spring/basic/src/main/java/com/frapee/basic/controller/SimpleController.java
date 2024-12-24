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

@RestController
@RequestMapping(path = "/simple")
@RequiredArgsConstructor
@Tag(name = "Simple", description = "Simple controller API (CRUD)")
/**
 * Simple Controller allowing access to the Simple CRUD operations
 */
public class SimpleController {

    private static final String EXAMPLE = "{\"id\":1,\"name\":\"apple\"}";
    
    @Autowired
    private SimpleService service;

    @GetMapping(value = "/{id}")
    @Operation(summary = "Read a simple record", description = "Read simple record by providing id to look up.", operationId="readsimple")
    @ApiResponses(value = { 
            @ApiResponse(responseCode = "200", description = "OK"), 
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })     
    /**
     * Retrieve simple record
     * @param id - id for a simple record
     * @return the simple record
     */
    public SimpleDto getOne(@Parameter(description = "Id for a simple record", example="1", required=true)
                             @PathVariable("id") int id) {
        return service.getOne(id);
    }

    @GetMapping
    @Operation(summary = "Read all existing simple records", description = "Read all of the simple record", operationId="allsimple")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "OK"), 
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })     
    /**
     * Retrieve all simple records in database
     * @return all simple records
     */
    public List<SimpleDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/search")
    @Operation(summary = "Search through simple records", description = "Pagable search for retrieving paged simple records.", operationId="searchsimple")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "OK"), 
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })     
    /**
     * Return part of the simple records in database, which is selected by providing paging information, page will start as 0
     * @param page - the actual page number requested
     * @param size - how many entries the page will return
     * @return sublist containing a page of simple records
     */
    public Page<SimpleDto> getSearch(@Parameter(description = "Current Page", example="1") 
                                      @RequestParam(defaultValue = "0") int page,
                                    @Parameter(description = "Page size", example="10") 
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
    @Operation(summary = "Create simple record", description = "Create new simple record and store to database.", operationId="createsimple")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "OK"), 
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })     
    /**
     * Create a new simple record
     * @param input - new simple record
     * @return the simple record created
     */
    public SimpleDto createOne(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Simple record to create", required = true,
            content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = SimpleDto.class),
                        examples = @ExampleObject(value = EXAMPLE)))
                @RequestBody @Valid SimpleDto input) {
        return service.createOne(input);
    }

    @PutMapping(value = "/{id}")
    @Operation(summary = "Update simple record", description = "Update the simple record by providing an id.", operationId="updatesimple")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "OK"), 
        @ApiResponse(responseCode = "404", description = "Not found"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })     
    /**
     * Update a the simple record
     * @param id - id for a simple record
     * @param input - changed simple record
     * @return the simple record updated
     */
    public SimpleDto updateOne(@Parameter(description = "Id for a simple record", example="1", required=true)
                                @PathVariable("id") int id,
                               @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    description = "Simple record to update", required = true,
                                    content = @Content(mediaType = "application/json",
                                                schema = @Schema(implementation = SimpleDto.class),
                                                examples = @ExampleObject(value = EXAMPLE)))
                                    @Valid @RequestBody SimpleDto input) {
        return service.updateOne(id, input);
    }

    @PatchMapping(value = "/{id}")
    @Operation(summary = "Partial update simple record", description = "Update the simple record by providing an id (as partial).", operationId="partialupdatesimple")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "OK"), 
        @ApiResponse(responseCode = "404", description = "Not found"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })    
    /**
     * Update the simple record, by using patch (note it only has one entry anyway)
     * @param id - id for a simple record
     * @param input - changed simple record
     * @return the simple record partially updated
     */
    public SimpleDto partialUpdateOne(@Parameter(description = "Id for a simple record", example="1", required=true)
                                       @PathVariable("id") int id, 
                                      @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                        description = "Simple record to partialy update", required = true,
                                        content = @Content(mediaType = "application/json",
                                                    schema = @Schema(implementation = SimpleDto.class),
                                                    examples = @ExampleObject(value = EXAMPLE)))
                                           @Valid @RequestBody SimpleDto input) {
        return service.updateOne(id, input);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete simple record", description = "Delete an existing simple record from database.", operationId="deletesimple")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "OK"), 
        @ApiResponse(responseCode = "404", description = "Not found"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })      
    /**
     * Remove a simple record with
     * @param id - id for a simple record
     */
    public void deleteOne(@Parameter(description = "Id for a simple record", example="1", required=true) 
                           @PathVariable("id") int id) {
        service.deleteOne(id);
    }

}
