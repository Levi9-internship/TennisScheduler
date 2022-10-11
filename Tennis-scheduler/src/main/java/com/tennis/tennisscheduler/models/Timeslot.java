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

    public Timeslot() {
    }

    public Timeslot(Long id, Date startDate, Date endDate, int duration, Person person, TennisCourt tennisCourt) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
        this.person = person;
        this.tennisCourt = tennisCourt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public TennisCourt getTennisCourt() {
        return tennisCourt;
    }

    public void setTennisCourt(TennisCourt tennisCourt) {
        this.tennisCourt = tennisCourt;
    }
}
