package com.frapee.validation;

/**
 * Validation results for all validation checks being done
 */
public class ValidationResult {
    private final boolean isValid;
    private final String message;

    /**
     * Constructor for Validation Result
     * @param isValid
     * @param message
     */
    public ValidationResult(boolean isValid, String message) {
        this.isValid = isValid;
        this.message = message;
    }

    /**
     * @return is valid
     */
    public boolean isValid() {
        return isValid;
    }

    /**
     * @return any error messages generated
     */
    public String getMessage() {
        return message;
    }
    
}
