package com.tennis.tennisscheduler.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.ObjectError;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeslotResponseDto {
 
    private TimeslotDto timeslot;
    private List<ObjectError> message;

    public TimeslotResponseDto(List<ObjectError> message) {
        this.message = message;
    }
}
