package com.frapee.basic.validation.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.frapee.basic.dto.SimpleDto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

public class TestSimpleDtoValidation {

    static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    static Stream<SimpleDto> validSimpleDtos() {
        return Stream.of(
            new SimpleDto(0, "abc"),
            new SimpleDto(1, "normal range"),
            new SimpleDto(2, "abcdefhijklmnopqrstuvwxyzabcdefhijklmnopqrstuvwx")
            );
    }

    static Stream<Arguments> inValidSimpleDtos() {
        return Stream.of(
            Arguments.of(new SimpleDto(0, ""), List.of("name", "name")),
            Arguments.of(new SimpleDto(1, null), List.of("name")),
            Arguments.of(new SimpleDto(2, "abcdefhijklmnopqrstuvwxyzabcdefhijklmnopqrstuvwxyzabcdef"), List.of("name"))
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("validSimpleDtos")
    public void testValid(SimpleDto valid) {
        assertThat(validator.validate(valid)).isEmpty();
    }

    @ParameterizedTest(name = "{0} invalid {1}")
    @MethodSource("inValidSimpleDtos")
    void testInvalid(SimpleDto invalid, List<String> invalidPaths) {
      assertThat(validator.validate(invalid))
          .extracting(violation -> violation.getPropertyPath().toString())
          .containsExactlyInAnyOrderElementsOf(invalidPaths);
    }
}
