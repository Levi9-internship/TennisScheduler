package com.tennis.tennisscheduler.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.ZonedDateTime;
@AllArgsConstructor
@Getter
@Setter
public class ApiException {

    private final String message;
    private final HttpStatus httpStatus;
    private final LocalDate zonedDateTime;
}
