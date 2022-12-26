package com.tennis.tennisscheduler.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@Table
@NoArgsConstructor
@AllArgsConstructor
public class WorkingTime {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column
    private long id;
    @Column
    private Time startWorkingTimeWeekDay;
    @Column
    private Time endWorkingTimeWeekDay;
    @Column
    private Time startWorkingTimeWeekend;
    @Column
    private Time endWorkingTimeWeekend;
}
