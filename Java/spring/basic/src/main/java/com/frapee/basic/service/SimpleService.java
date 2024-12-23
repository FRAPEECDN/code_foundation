package com.frapee.basic.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.frapee.basic.dto.SimpleDto;
import com.frapee.basic.entities.SimpleEntity;
import com.frapee.basic.exceptions.GeneralServiceException;
import com.frapee.basic.repository.SimpleRepository;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
/**
 * Simple Service, allows interaction with using Simple DTO and Entity to do CRUD operations
 */
public class SimpleService {

    @Autowired
    private SimpleRepository repository;

    /**
     * Service returns all entities stored in the database
     * @return list of Simple Dto entries
     */
    public @Nonnull List<SimpleDto> getAll() {
        log.info("Retrievig all items from the database");
        Iterable<SimpleEntity> result = repository.findAll();
        Stream<SimpleEntity> stream = StreamSupport.stream(result.spliterator(), false);
        return stream.map(e -> new SimpleDto(e.getId(), e.getName()))
                     .collect(Collectors.toList());
    }

    /**
     * Service returns one entity from the database
     * @param id - the id of the entity to return
     * @return single Simple Dto entry
     */
    public @Nullable SimpleDto getOne(int id) {
        log.info("Retrieving item from the database");
        Optional<SimpleEntity> result = repository.findById(id);
        if (result.isPresent()) {
            return new SimpleDto(result.get().getId(), result.get().getName());
        } else {
            throw new ResourceNotFoundException();
        }
    }

    /**
     * Service creates new entity in the database
     * @param input - Simple Dto to add
     * @return newly created Dto from entity in database
     */
    public @Nonnull SimpleDto createOne(@Nullable SimpleDto input) {
        log.info("Adding new item to the database");
        if (input == null) {
            throw new NullPointerException();
        }
        SimpleEntity newEntity = new SimpleEntity(input.id(), input.name());
        SimpleEntity result;
        try {
            result = repository.save(newEntity);
        } catch (IllegalArgumentException ex) {
            throw new GeneralServiceException();
        }
        return new SimpleDto(result.getId(), result.getName());
    }
    
    /**
     * Service updates simple entity in database with specified id
     * @param id - the id of the entity to replace
     * @param newValue - Dto information for modification
     * @return modified Dto from entity in database
     */
    public SimpleDto updateOne(int id, @Nullable SimpleDto newValue)  {
        log.info("Changing an item with id from database, with changed values");
        if (newValue == null) {
            throw new NullPointerException();
        }
        Optional<SimpleEntity> lookup = repository.findById(id);
        if (!lookup.isPresent()) {
            throw new ResourceNotFoundException();
        }
        SimpleEntity changedEntity = new SimpleEntity(newValue.id(), newValue.name());
        SimpleEntity result;
        try {
            result = repository.save(changedEntity);
        } catch (IllegalArgumentException ex) {
            throw new GeneralServiceException();
        }
        return new SimpleDto(result.getId(), result.getName());        
    }

    /**
     * Service removes simple entity from database
     * @param id - the id of the entity to remove
     */
    public void deleteOne(int id) {
        log.info("Removing an item from the database");
        Optional<SimpleEntity> lookup = repository.findById(id);
        if (lookup.isPresent()) {
            repository.delete(lookup.get());
        } else {
            throw new ResourceNotFoundException();
        }
    }

}
