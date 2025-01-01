package com.frapee.basic_postgres.exceptions;

/**
 * Service exception for when issue happens (runtime exception)
 */
public class GeneralServiceException extends RuntimeException {

    public GeneralServiceException() {
        super("Runtime exception generated during service execution");
    }

}
