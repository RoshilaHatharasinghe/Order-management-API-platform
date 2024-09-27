package com.APIPlatform.API_platform.Exception;

import com.APIPlatform.API_platform.Response.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle cases where the user is already registered in the system
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    // Handle validation errors for method arguments (e.g., invalid input fields)
    /**
     * Handles validation exceptions from request bodies that do not meet defined criteria.
     * This method processes MethodArgumentNotValidException, extracting the fields with validation errors
     * and returning a map of field names to error messages.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // Handle cases where the username is not found during authentication
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    // Handle cases where a requested order cannot be found
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleOrderNotFoundException(OrderNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    // Handle cases where an order has already been dispatched and cannot be modified
    @ExceptionHandler(OrderDispatchedException.class)
    public ResponseEntity<ErrorResponse> handleOrderDispatchedException(OrderDispatchedException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    // Handle cases where an order is already cancelled and the user tries to cancel it again
    @ExceptionHandler(OrderAlreadyCancelledException.class)
    public ResponseEntity<ErrorResponse> handleOrderAlreadyCancelledException(OrderAlreadyCancelledException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    // Handle general exceptions, focusing on security-related exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleSecurityException(Exception e) {

        if (e instanceof BadCredentialsException) {
            ErrorResponse errorResponse = new ErrorResponse("The username or password is incorrect");
            return ResponseEntity.status(401).body(errorResponse);
        }

        if (e instanceof AccountStatusException) {
            ErrorResponse errorResponse = new ErrorResponse("The account is locked");
            return ResponseEntity.status(401).body(errorResponse);
        }

        if (e instanceof AccessDeniedException) {
            ErrorResponse errorResponse = new ErrorResponse("You are not authorized to access this resource");
            return ResponseEntity.status(401).body(errorResponse);
        }

        if (e instanceof SignatureException) {
            ErrorResponse errorResponse = new ErrorResponse("The JWT signature is invalid");
            return ResponseEntity.status(401).body(errorResponse);
        }

        if (e instanceof ExpiredJwtException) {
            ErrorResponse errorResponse = new ErrorResponse("The JWT token has expired");
            return ResponseEntity.status(401).body(errorResponse);
        }

        else {
            ErrorResponse errorResponse = new ErrorResponse("Unknown internal server error.");
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
}
