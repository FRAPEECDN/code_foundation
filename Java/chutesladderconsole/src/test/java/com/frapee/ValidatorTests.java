package com.frapee;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.frapee.board.Board;
import com.frapee.board.Board10x10;
import com.frapee.validation.ChuteValidator;
import com.frapee.validation.LadderValidator;
import com.frapee.validation.PlayerValidator;
import com.frapee.validation.SquarePositionValidator;
import com.frapee.validation.ValidationResult;

/**
 * Tests for validators
 */
public class ValidatorTests {

    @Test
    public void validatePositionTest() {
        Board testBoard = new Board10x10();
        SquarePositionValidator validator = new SquarePositionValidator(testBoard);
        ValidationResult result = validator.validate(-1);
        assertFalse(result.isValid());
        assertThat(result.getMessage(), not(is(emptyOrNullString())));
        result = validator.validate(120);
        assertFalse(result.isValid());
        assertThat(result.getMessage(), not(is(emptyOrNullString())));
        result = validator.validate(50);
        assertTrue(result.isValid());
        assertThat(result.getMessage(), is(emptyOrNullString()));
    }

    @Test
    public void validateChuteTest() {
        Board testBoard = new Board10x10();
        ChuteValidator validator = new ChuteValidator(testBoard);
        ValidationResult result = validator.validate(1);
        assertFalse(result.isValid());
        assertThat(result.getMessage(), not(is(emptyOrNullString())));
        result = validator.validate(-20);
        assertFalse(result.isValid());
        assertThat(result.getMessage(), not(is(emptyOrNullString())));        
        result = validator.validate(-4);
        assertTrue(result.isValid());
        assertThat(result.getMessage(), is(emptyOrNullString()));
    }

    @Test
    public void validateLadderTest() {
        Board testBoard = new Board10x10();
        LadderValidator validator = new LadderValidator(testBoard);
        ValidationResult result = validator.validate(-1);
        assertFalse(result.isValid());
        assertThat(result.getMessage(), not(is(emptyOrNullString())));
        result = validator.validate(20);
        assertFalse(result.isValid());
        assertThat(result.getMessage(), not(is(emptyOrNullString())));        
        result = validator.validate(4);
        assertTrue(result.isValid());
        assertThat(result.getMessage(), is(emptyOrNullString()));
    }

    @Test
    public void validatePlayerTest() {
        PlayerValidator validator = new PlayerValidator();
        ValidationResult result = validator.validate("");        
        assertFalse(result.isValid());
        assertThat(result.getMessage(), not(is(emptyOrNullString())));
        result = validator.validate("%^&^&$");
        assertFalse(result.isValid());
        assertThat(result.getMessage(), not(is(emptyOrNullString())));        
        result = validator.validate("Aa09");
        assertTrue(result.isValid());
        assertThat(result.getMessage(), is(emptyOrNullString()));
    }

}
