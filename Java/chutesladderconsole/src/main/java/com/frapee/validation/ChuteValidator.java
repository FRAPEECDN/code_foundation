package com.frapee.validation;

import com.frapee.board.Board;

/**
 * Validator to use with board setup, indicate Chute length and direction is correct.
 */
public class ChuteValidator implements Validator<Integer> {

    private int max;

    public ChuteValidator(Board board) {
        this.max = board.getConfig().getChuteMax();
    }

    @Override
    public ValidationResult validate(Integer t) {
        if (t > 0) {
            return new ValidationResult(false, "Chute direction is not valid.");
        } else if (t < -1 * max) {
            return new ValidationResult(false, "Chute length cannot subceed " + max + ".");
        } else {
            return new ValidationResult(true, "");
        }
    }    
}
