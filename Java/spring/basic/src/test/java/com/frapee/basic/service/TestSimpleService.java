package com.frapee.basic.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.blankOrNullString;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import com.frapee.basic.dto.SimpleDto;
import com.frapee.basic.entities.SimpleEntity;
import com.frapee.basic.exceptions.GeneralServiceException;
import com.frapee.basic.repository.SimpleRepository;

@ExtendWith(MockitoExtension.class)
/**
 * Testing the Simple Service CRUD operations directly
 */
public class TestSimpleService {

    @InjectMocks
    private SimpleService testService;

    @Mock
    private SimpleRepository testRepository;

    private List<SimpleEntity> setupData;

    @BeforeEach
    void setUp() {
        setupData = List.of(
                new SimpleEntity(0, "apple"),
                new SimpleEntity(0, "pear"),
                new SimpleEntity(0, "peach"),
                new SimpleEntity(0, "grape"),
                new SimpleEntity(0, "orange")
        );
    }

    @Test
    public void testGetAll() {
        when(testRepository.findAll()).thenReturn(setupData);

        List<SimpleDto> results = testService.getAll();
        assertThat(results.size(), equalTo(setupData.size()));
        for (SimpleDto entry : results) {
            assertThat(entry.name(), not(blankOrNullString()));
        }
    }

    @Test
    public void testGetOne() {
        when(testRepository.findById(1)).thenReturn(Optional.of(setupData.get(1)));

        SimpleDto result = testService.getOne(1);
        assertThat(result, notNullValue());
        assertThat(result.name(), not(blankOrNullString()));
    }

    @Test
    public void testCreate() {
        when(testRepository.save(isA(SimpleEntity.class))).thenReturn(new SimpleEntity(1, "newValue"));
        SimpleDto value = new SimpleDto(0, "newValue");
        SimpleDto newValue = testService.createOne(value);
        assertThat(newValue.name(), not(blankOrNullString()));
        assertThat(newValue.name(), equalTo(value.name()));
    }

    @Test
    public void testUpdate() {
        when(testRepository.findById(1)).thenReturn(Optional.of(setupData.get(1)));
        when(testRepository.save(isA(SimpleEntity.class))).thenReturn(new SimpleEntity(1, "changedValue"));

        SimpleDto oldValue = testService.getOne(1);
        SimpleDto newValue = new SimpleDto(oldValue.id(), "changedValue");
        SimpleDto updateResult = testService.updateOne(1, newValue);
        assertThat(updateResult.name(), not(blankOrNullString()));
        assertThat(updateResult.name(), equalTo(newValue.name()));
    }

    @Test
    public void testDelete() {
        when(testRepository.findById(1)).thenReturn(Optional.of(setupData.get(1)));
        Mockito.doNothing().when(testRepository).delete(isA(SimpleEntity.class));
        testService.deleteOne(1);
    }

    @Test
    public void testCreateWithNullFail() {
        SimpleDto value = null;
        Exception exception = assertThrows(NullPointerException.class, () -> {
            testService.createOne(value);
        });
        assertThat(exception.getMessage(), blankOrNullString());
    }

    @Test
    public void testGetOneFail() {
        when(testRepository.findById(isA(Integer.class))).thenReturn(Optional.empty());
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            testService.getOne(10);
        });
        assertThat(exception.getMessage(), not(blankOrNullString()));
    }

    @Test
    public void testCreateFail() {
        when(testRepository.save(isA(SimpleEntity.class))).thenThrow(IllegalArgumentException.class);
        Exception exception = assertThrows(GeneralServiceException.class, () -> {
            testService.createOne(new SimpleDto(10, "Coconut"));
        });
        assertThat(exception.getMessage(), not(blankOrNullString()));
    }    

    @Test
    public void testUpdateFail() {
        when(testRepository.findById(isA(Integer.class))).thenReturn(Optional.empty());
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            testService.updateOne(10, new SimpleDto(10, "Coconut"));
        });
        assertThat(exception.getMessage(), not(blankOrNullString()));
    }

    @Test
    public void testUpdateSaveFail() {
        when(testRepository.findById(isA(Integer.class))).thenReturn(Optional.of(setupData.get(1)));
        when(testRepository.save(isA(SimpleEntity.class))).thenThrow(IllegalArgumentException.class);
        Exception exception = assertThrows(GeneralServiceException.class, () -> {
            testService.updateOne(10, new SimpleDto(10, "Coconut"));
        });
        assertThat(exception.getMessage(), not(blankOrNullString()));
    }    

    @Test
    public void testDeleteFail() {
        when(testRepository.findById(isA(Integer.class))).thenReturn(Optional.empty());
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            testService.deleteOne(10);
        });
        assertThat(exception.getMessage(), not(blankOrNullString()));
    }

}
