package com.frapee.basic_postgres.validation.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.context.ActiveProfiles;

import com.frapee.basic_postgres.entities.SimpleEntity;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

@ActiveProfiles("test")
public class TestSimpleEntityValidation {

    static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    static Stream<SimpleEntity> validSimpleEntities() {
        return Stream.of(
            SimpleEntity.builder().id(0).name("a").build(),
            SimpleEntity.builder().id(1).name("abc").build(),
            SimpleEntity.builder().id(2).name("normal range").build(),
            SimpleEntity.builder().id(3).name("abcdefhijklmnopqrstuvwxyzabcdefhijklmnopqrstuvwx").build()
        );
    }

    static Stream<Arguments> inValidSimpleEntities() {
        return Stream.of(
            // Arguments.of(SimpleEntity.builder().id(0).name("").build(), List.of("name")),
            Arguments.of(SimpleEntity.builder().id(1).name(null).build(), List.of("name")),
            Arguments.of(SimpleEntity.builder().id(2).name("abcdefhijklmnopqrstuvwxyzabcdefhijklmnopqrstuvwxyzabcdef").build(), List.of("name"))
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("validSimpleEntities")
    public void testValid(SimpleEntity valid) {
        assertThat(validator.validate(valid)).isEmpty();
    }

    @ParameterizedTest(name = "{0} invalid {1}")
    @MethodSource("inValidSimpleEntities")
    void testInvalid(SimpleEntity invalid, List<String> invalidPaths) {
      assertThat(validator.validate(invalid))
          .extracting(violation -> violation.getPropertyPath().toString())
          .containsExactlyInAnyOrderElementsOf(invalidPaths);
    }

}
