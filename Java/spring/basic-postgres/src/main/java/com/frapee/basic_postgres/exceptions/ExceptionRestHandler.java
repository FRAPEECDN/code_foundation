package com.frapee.basic_postgres.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
/**
 * Global exception class to handle errors that can happen
 */
public class ExceptionRestHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = { 
        IllegalArgumentException.class, 
        IllegalStateException.class 
    })
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        String responseBody = "Internal conflict happened.";
        return handleExceptionInternal(ex, responseBody, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    protected ResponseEntity<Object> handleNotFound(RuntimeException ex, WebRequest request) {
        String reponseBody = "Request resource not found";
        return handleExceptionInternal(ex, reponseBody, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {GeneralServiceException.class}) 
    protected ResponseEntity<Object> handleServiceError(RuntimeException ex, WebRequest request) {
        String reponseBody = "Server error";
        return handleExceptionInternal(ex, reponseBody, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
