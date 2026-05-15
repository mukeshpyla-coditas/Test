package com.example.courseManagementProject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> responseEntity(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(
                error -> errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(InvalidArgumentException.class)
    public ResponseEntity<ErrorResponse> errorResponse(InvalidArgumentException exception) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), LocalDate.now()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> errorResponse(EntityNotFoundException exception) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), LocalDate.now()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenExpirationException.class)
    public ResponseEntity<ErrorResponse> errorResponse(TokenExpirationException exception) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), LocalDate.now()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EnrollmentException.class)
    public ResponseEntity<ErrorResponse> errorResponse(EnrollmentException exception) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), LocalDate.now()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IncompleteCourseException.class)
    public ResponseEntity<ErrorResponse> errorResponse(IncompleteCourseException exception) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), LocalDate.now()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TestNotPassedException.class)
    public ResponseEntity<ErrorResponse> errorResponse(TestNotPassedException exception) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), LocalDate.now()), HttpStatus.BAD_REQUEST);
    }
}
