package healli.foodie.common.exception;


import healli.foodie.common.validation.EntityAttributeError;
import healli.foodie.common.validation.EntityAttributeNotValidException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail onConstraintViolationException(ConstraintViolationException ex) {

        List<String> errorMessages = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errorMessages.add(violation.getMessage());
        }
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setProperty("Timestamp", LocalDateTime.now());
        problemDetail.setProperty("Errors", errorMessages);
        return problemDetail;
    }

    @ExceptionHandler(EntityAttributeNotValidException.class)
    public ProblemDetail onEntityAttributeNotValidException(EntityAttributeNotValidException ex) {

        List<String> errorMessages = new ArrayList<>();
        for (EntityAttributeError error : ex.getErrors()) {
            errorMessages.add(error.getMessage());
        }
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setProperty("Timestamp", LocalDateTime.now());
        problemDetail.setProperty("Errors", errorMessages);
        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail onMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        List<String> errorMessages = new ArrayList<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errorMessages.add(fieldError.getDefaultMessage());

        }
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setProperty("Timestamp", LocalDateTime.now());
        problemDetail.setProperty("Errors", errorMessages);
        return problemDetail;
    }
}