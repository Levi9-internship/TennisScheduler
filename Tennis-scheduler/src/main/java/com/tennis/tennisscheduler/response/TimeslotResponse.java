package com.tennis.tennisscheduler.response;

import com.tennis.tennisscheduler.models.Timeslot;
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
public class TimeslotResponse {
    private List<ObjectError> message;
    private Timeslot timeslot;
}
