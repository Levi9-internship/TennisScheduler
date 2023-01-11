package com.tennis.tennisscheduler.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkingTime {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private Time startWorkingTimeWeekDay;
    private Time endWorkingTimeWeekDay;
    private Time startWorkingTimeWeekend;
    private Time endWorkingTimeWeekend;
}
