package com.frapee.basic_postgres.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.frapee.basic_postgres.entities.SimpleEntity;

@Repository
/**
 * Repository to suport Simple Data
 */
public interface SimpleRepository extends CrudRepository<SimpleEntity, Integer> {

}
