package com.tennis.tennisscheduler.dtos;

import org.springframework.validation.ObjectError;

import java.util.List;

public class TimeslotResponseDto {
 
    public TimeslotDto timeslot;

    public List<ObjectError> message;
}
