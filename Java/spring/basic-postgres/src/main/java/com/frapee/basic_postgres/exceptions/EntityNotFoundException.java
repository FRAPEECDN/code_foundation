package com.frapee.basic_postgres.exceptions;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException() {
        super("Entity was not found");
    }

}
