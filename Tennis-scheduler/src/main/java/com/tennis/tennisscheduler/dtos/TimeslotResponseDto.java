package com.tennis.tennisscheduler.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.ObjectError;

import java.util.List;

@Getter
@Setter
public class TimeslotResponseDto {
 
    private TimeslotDto timeslot;

    private List<ObjectError> message;
}
