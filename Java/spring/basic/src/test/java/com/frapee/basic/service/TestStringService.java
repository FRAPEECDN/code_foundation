package com.frapee.basic.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
/**
 * Testing the string service directly (to make sure that layer works)
 * Mockito - Create the service and will inject mocking objects for what being used
 * in this case there is nothing to mock since String Service emulates storing objects
 */
public class TestStringService {

    @InjectMocks
    private StringService testService;

    @BeforeEach
    void setUp() {
        List<String> setupData = List.of(
                "apple",
                "pear",
                "peach",
                "grape",
                "orange"
        );
        testService.setInternalStrings(setupData);
    }

    @AfterEach
    void tearDown() {
        testService.resetRepositoryList();
    }

    @Test
    public void testGetAll() {
        List<String> results = testService.getAll();
        assertThat(results.size()).isEqualTo(5);
        testService.resetRepositoryList();
        results = testService.getAll();
        assertThat(results).isEmpty();
    }

    @Test
    public void testGetOne() {
        String result = testService.getOne(1);
        assertThat(result).isNotNull();
        assertThat(result).isNotBlank();
    }

    @Test
    public void testCreate() {
        int countStart = testService.getAll().size();
        String value = "Coconut";
        int indexResult = testService.createOne(value);
        assertThat(indexResult).isGreaterThan(0);
        String testAdded = testService.getOne(indexResult);
        assertThat(testAdded).isNotNull();
        assertThat(testAdded).isNotBlank();
        int countCurrent = testService.getAll().size();
        assertThat(countCurrent).isGreaterThan(countStart);
    }

    @Test
    public void testUpdate() {
        String oldValue = testService.getOne(1);
        String newValue = "Coconut";
        String updateResult = testService.updateOne(1, newValue);
        assertThat(updateResult).isEqualTo(newValue); // Please see set of List for explanation
        String currentValue = testService.getOne(1);
        assertThat(currentValue).isNotEqualTo(oldValue);
    }

    @Test
    public void testDelete() {
        int countStart = testService.getAll().size();
        String oldValue = testService.getOne(1);
        testService.deleteOne(1);
        int countCurrent = testService.getAll().size();
        String currentValue = testService.getOne(1);
        assertThat(countCurrent).isLessThan(countStart);
        assertThat(currentValue).isNotEqualTo(oldValue);
    }

    @Test
    public void testCreateFail() {
        String value = "apple";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            testService.createOne(value);
        });
        assertThat(exception.getMessage()).isNotBlank();
    }

    @Test
    public void testCreateWithNullFail() {
        String value = null;
        Exception exception = assertThrows(NullPointerException.class, () -> {
            testService.createOne(value);
        });
        assertThat(exception.getMessage()).isNull();;
    }


    @Test
    public void testGetOneFail() {
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            testService.getOne(10);
        });
        assertThat(exception.getMessage()).isNotBlank();
    }

    @Test
    public void testUpdateFail() {
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            testService.updateOne(10, "Coconut");
        });
        assertThat(exception.getMessage()).isNotBlank();
    }

    @Test
    public void testDeleteFail() {
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            testService.deleteOne(10);
        });
        assertThat(exception.getMessage()).isNotBlank();
    }
    
}
