package com.editorflow.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.validation.FieldError;

import com.editorflow.dto.common.ErrorResponse;
import com.editorflow.dto.common.ValidationError;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErrorResponse> handleValidationException(
                        MethodArgumentNotValidException ex) {

                List<ValidationError> errors = ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(fieldError -> new ValidationError(
                                                fieldError.getField(),
                                                fieldError.getDefaultMessage()))
                                .toList();

                ErrorResponse response = new ErrorResponse(
                                false,
                                "Validation Failed",
                                errors);

                return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .body(response);
        }

        @ExceptionHandler(ProjectNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleProjectNotFoundException(
                        ProjectNotFoundException ex) {

                ErrorResponse response = new ErrorResponse(
                                false,
                                ex.getMessage(),
                                null);

                return ResponseEntity
                                .status(HttpStatus.NOT_FOUND)
                                .body(response);
        }

        @ExceptionHandler(EmailAlreadyExistsException.class)
        public ResponseEntity<ErrorResponse> handleEmailAlreadyExistsException(
                        EmailAlreadyExistsException ex) {

                ErrorResponse response = new ErrorResponse(
                                false,
                                ex.getMessage(),
                                null);

                return ResponseEntity
                                .status(HttpStatus.CONFLICT)
                                .body(response);
        }

        @ExceptionHandler(AccessDeniedException.class)
        public ResponseEntity<ErrorResponse> handleAccessDeniedException(
                        AccessDeniedException ex) {

                ErrorResponse response = new ErrorResponse(
                                false,
                                "You do not have permission to perform this action.",
                                null);

                return ResponseEntity
                                .status(HttpStatus.FORBIDDEN)
                                .body(response);
        }
}