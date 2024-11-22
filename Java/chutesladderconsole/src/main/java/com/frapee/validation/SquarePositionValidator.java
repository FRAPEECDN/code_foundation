package com.frapee.validation;

import com.frapee.board.Board;

/**
 * Validator to use with board setup, indicate Square positioning is valid
 */
public class SquarePositionValidator implements Validator<Integer> {

    private int max;

    public SquarePositionValidator(Board board) {
        this.max = board.getConfig().getBoardSize();
    }

    @Override
    public ValidationResult validate(Integer t) {
        if (t < 1) {
            return new ValidationResult(false, "Square is not on the board, it is below starting point");
        } else if (t > max) {
            return new ValidationResult(false, "Square is not on the board, it it beyond board ending position of " + max + ".");
        } else {
            return new ValidationResult(true, "");
        }
    }

}
