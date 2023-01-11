package com.tennis.tennisscheduler.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
public class WorkingTimeDto {

    private long id;
    private Time startWorkingTimeWeekDay;
    private Time endWorkingTimeWeekDay;
    private Time startWorkingTimeWeekend;
    private Time endWorkingTimeWeekend;
}
