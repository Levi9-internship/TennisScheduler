package com.tennis.tennisscheduler.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ApiRequestException extends ResponseStatusException {


    public ApiRequestException(HttpStatus status, String reason) {
        super(status, reason);
    }

    public ApiRequestException(HttpStatus status, String reason, Throwable cause) {
        super(status, reason, cause);
    }
}
