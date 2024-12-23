package com.frapee.basic.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.frapee.basic.entities.SimpleEntity;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TestSimpleRepository {

    @SuppressWarnings("unused")
    @Autowired
    private TestEntityManager testEM;

    @Autowired
    private SimpleRepository repository;

   @Test
    public void testSaveAndGet() {
        SimpleEntity aEntity = SimpleEntity.builder()
            .id(0)
            .name("test")
            .build();

        repository.save(aEntity);
        int savedId = aEntity.getId();
        Optional<SimpleEntity> testEntity = repository.findById(savedId);

        assertTrue(testEntity.isPresent());
        assertThat(testEntity.get(), notNullValue());
        assertThat(testEntity.get().getId(), equalTo(aEntity.getId()));
        assertThat(testEntity.get().getName(), equalTo(aEntity.getName()));
    }

    @Test
    public void testSaveAndGetMultiple() {
        SimpleEntity aEntity = SimpleEntity.builder()
            .id(1)
            .name("test One")
            .build();

        repository.save(aEntity);
        int aSavedId = aEntity.getId();

        SimpleEntity bEntity = SimpleEntity.builder()
            .id(2)
            .name("test Two")
            .build();

        repository.save(bEntity);
        int bSavedId = bEntity.getId();

        Iterable<SimpleEntity> testList = repository.findAllById(List.of(aSavedId, bSavedId));
        assertThat(testList, notNullValue());
        Iterator<SimpleEntity> iterator = testList.iterator();
        SimpleEntity check = iterator.next();
        assertThat(check, notNullValue());
        assertThat(check.getId(), equalTo(aEntity.getId()));
        assertThat(check.getName(), equalTo(aEntity.getName()));
        check = iterator.next();
        assertThat(check, notNullValue());
        assertThat(check.getId(), equalTo(bEntity.getId()));
        assertThat(check.getName(), equalTo(bEntity.getName()));
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testUpdate() {
        SimpleEntity aEntity = SimpleEntity.builder()
            .id(0)
            .name("test")
            .build();

        repository.save(aEntity);
        int savedId = aEntity.getId();
        Optional<SimpleEntity> createdEntity = repository.findById(savedId);
        assertTrue(createdEntity.isPresent());
        assertThat(createdEntity.get(), notNullValue());
        createdEntity.get().setName("changed");


        Optional<SimpleEntity> updatedEntity = repository.findById(savedId);
        assertTrue(updatedEntity.isPresent());
        assertThat(updatedEntity.get(), notNullValue());
        assertThat(updatedEntity.get().getName(), equalTo("changed"));
    }

    @Test
    public void testDelete() {
        SimpleEntity aEntity = SimpleEntity.builder()
            .id(0)
            .name("test")
            .build();

        repository.save(aEntity);
        int savedId = aEntity.getId();        
        Optional<SimpleEntity> createdEntity = repository.findById(savedId);
        assertTrue(createdEntity.isPresent());
        assertThat(createdEntity.get(), notNullValue());
        repository.delete(aEntity);

        Optional<SimpleEntity> checkEntity = repository.findById(savedId);
        assertFalse(checkEntity.isPresent());
    }
  
}
