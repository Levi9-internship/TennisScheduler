package com.tennis.tennisscheduler.exception;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class ErrorDetails {

    private String message;
    private String error;
    private Long timestamp;
    private Integer status;
    private String path;
}
