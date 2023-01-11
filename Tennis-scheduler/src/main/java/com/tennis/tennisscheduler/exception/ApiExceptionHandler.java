package com.tennis.tennisscheduler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;

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

    @ExceptionHandler(value = {HttpClientErrorException.BadRequest.class})
    public ResponseEntity<ErrorDetails> handleApiRequestException(HttpClientErrorException.BadRequest e, WebRequest webRequest){

        final ErrorDetails errorDetails = ErrorDetails
                .builder()
                .timestamp(currentTimeMillis())
                .status(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .path("BAD REQUEST")
                .build();
        return new ResponseEntity<>(errorDetails,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {HttpClientErrorException.Unauthorized.class})
    public ResponseEntity<ErrorDetails> handleApiRequestException(HttpClientErrorException.Unauthorized e, WebRequest webRequest){

        final ErrorDetails errorDetails = ErrorDetails
                .builder()
                .timestamp(currentTimeMillis())
                .status(HttpStatus.UNAUTHORIZED.value())
                .message(e.getMessage())
                .path("You don't have permission")
                .build();
        return new ResponseEntity<>(errorDetails,HttpStatus.UNAUTHORIZED);
    }
}
