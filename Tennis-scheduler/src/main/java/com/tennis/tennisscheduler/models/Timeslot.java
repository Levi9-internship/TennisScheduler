package com.tennis.tennisscheduler.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class Timeslot {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column
    private Date startDate;
    @Column
    private Date endDate;
    @Column
    private int duration;
    @ManyToOne
    @JoinColumn(name="person_id")
    private Person person;
    @ManyToOne
    @JoinColumn(name="tennis_court_id")
    private TennisCourt tennisCourt;
}
