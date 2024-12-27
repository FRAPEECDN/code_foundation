package com.frapee.validation;

/**
 * Player validator
 */
public class PlayerValidator implements Validator<String> {

    /**
     * Constructor
     */
    public PlayerValidator() {
    }

    @Override
    public ValidationResult validate(String t) {
        if (t.isEmpty()) {
            return new ValidationResult(false, "cannot be empty");
        }

        if (!t.matches("^[a-zA-Z0-9]+$")) {
            return new ValidationResult(false, "must have only AlphaNumeric value");
        }

        return new ValidationResult(true, "");
    }
    
}
