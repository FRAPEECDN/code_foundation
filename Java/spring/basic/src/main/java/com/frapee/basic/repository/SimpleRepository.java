package com.frapee.basic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.frapee.basic.entities.SimpleEntity;

@Repository
/**
 * Repository to suport Simple Data
 */
public interface SimpleRepository extends CrudRepository<SimpleEntity, Integer> {

}
