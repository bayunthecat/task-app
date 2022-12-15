package com.mine.app.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicatedEntityException.class)
    public ResponseEntity<Object> handleConstraintViolationException(DuplicatedEntityException exception, WebRequest request) {
        return ResponseEntity
                .badRequest()
                .body(exception);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException exception, WebRequest request) {
        return ResponseEntity
                .notFound()
                .build();
    }
}
