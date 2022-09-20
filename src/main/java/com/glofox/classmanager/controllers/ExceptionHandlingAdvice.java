package com.glofox.classmanager.controllers;

import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExceptionHandlingAdvice {
    private final static Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlingAdvice.class);

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException exception, WebRequest request) {
        LOGGER.debug("Illegal argument: {}. Request: {}", exception.getMessage(), request);
        return new ResponseEntity<>(Map.of("error", exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<Map<String, String>> handleNoSuchElement(NoSuchElementException exception, WebRequest request) {
        LOGGER.debug("No such element: {}. Request: {}", exception.getMessage(), request);
        return new ResponseEntity<>(Map.of("error", exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DateTimeParseException.class)
    protected ResponseEntity<Map<String, String>> handleInvalidDate(DateTimeParseException exception, WebRequest request) {
        LOGGER.debug("Invalid date argument: {}. Request: {}", exception.getMessage(), request);
        return new ResponseEntity<>(Map.of("error", "Invalid date provided"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Map<String, String>> handleInvalidArgument(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        LOGGER.debug("Invalid arguments: {}", errors);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
