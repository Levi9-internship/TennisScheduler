package com.tennis.tennisscheduler.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WorkingTime {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column
    private long id;
    @Column
    private Date startWorkingTimeWeekDay;
    @Column
    private Date endWorkingTimeWeekDay;
    @Column
    private Date startWorkingTimeWeekend;
    @Column
    private Date endWorkingTimeWeekend;
    @ManyToOne(fetch = FetchType.EAGER,cascade =  CascadeType.ALL)
    private TennisCourt tennisCourt;
}
