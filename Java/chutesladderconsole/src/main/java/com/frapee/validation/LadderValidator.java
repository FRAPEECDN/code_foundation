package com.frapee.validation;

import com.frapee.board.Board;

/**
 * Validator to use with board setup, indicate ladder length and direction is correct.
 */
public class LadderValidator implements Validator<Integer> {

    private int max;

    public LadderValidator(Board board) {
        this.max = board.getConfig().getLadderMax();
    }

    @Override
    public ValidationResult validate(Integer t) {
        if (t < 0) {
            return new ValidationResult(false, "Ladder direction is not correct.");
        } else if (t > max) {
            return new ValidationResult(false, "Ladder length cannot exceed " + max + ".");
        } else {
            return new ValidationResult(true, "");
        }
    }     
}
