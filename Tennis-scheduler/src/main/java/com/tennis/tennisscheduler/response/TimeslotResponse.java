package com.tennis.tennisscheduler.response;

import com.tennis.tennisscheduler.models.Timeslot;
import org.springframework.validation.ObjectError;

import java.util.List;

public class TimeslotResponse {
    public List<ObjectError> message;
    public Timeslot timeslot;
}
