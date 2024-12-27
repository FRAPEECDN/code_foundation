package com.frapee.validation;

/**
 * Validator interface for validating.
 * It can chain validations together with logical operations
 */
@FunctionalInterface
public interface Validator<T> {

    /**
     * Validate blueprint for all specific validations
     * @param t - input for validation
     * @return - result for validation
     */
    ValidationResult validate(T t);

    /**
     * Chain validation together using logical and
     * @param other - other validation
     * @return - combined results
     */
    default Validator<T> and(Validator<? super T> other) {
        return obj -> {
            ValidationResult result = this.validate(obj);
            return !result.isValid() ? result : other.validate(obj);
        };
    }

    /**
     * Chain validation together using logical or
     * @param other - other validation
     * @return - combined results
     */
    default Validator<T> or(Validator<? super T> other) {
        return obj -> {
            ValidationResult result = this.validate(obj);
            return result.isValid() ? result : other.validate(obj);
        };
    }
    
    /**
     * Negate result running logical not
     * @return negated results
     */
    default Validator<T> negate() {
        return obj -> {
            ValidationResult result = this.validate(obj);
            return new ValidationResult(!result.isValid(), result.getMessage());
        };
    }

}
