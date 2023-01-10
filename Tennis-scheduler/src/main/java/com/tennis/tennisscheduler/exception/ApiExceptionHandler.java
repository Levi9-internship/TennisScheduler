package com.tennis.tennisscheduler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static java.lang.System.*;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e){

        ApiException apiException = new ApiException(e.getMessage(), e.getStatus(), LocalDate.now());
        return new ResponseEntity<>(apiException,e.getStatus());
    }
    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<ErrorDetails> handleApiRequestException(EntityNotFoundException e, WebRequest webRequest){

        final ErrorDetails errorDetails = ErrorDetails
                .builder()
                .timestamp(currentTimeMillis())
                .status(HttpStatus.NOT_FOUND.value())
                .message(e.getMessage())
                .path("Resource NOT FOUND")
                .build();
        return new ResponseEntity<>(errorDetails,HttpStatus.NOT_FOUND);
    }
}
