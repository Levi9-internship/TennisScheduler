package com.tennis.tennisscheduler.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class Timeslot {

    @Id
    @SequenceGenerator(name = "timeslotSeqGen", sequenceName = "timeslotSeqGen", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "timeslotSeqGen")
    @Column(name="id", unique=true, nullable=false)
    private Long id;
    @Column
    private Date startDate;
    @Column
    private Date endDate;
    @ManyToOne
    @JoinColumn(name="person_id")
    @JsonIgnore
    private Person person;
    @ManyToOne
    @JoinColumn(name="tennis_court_id")
    @JsonIgnore
    private TennisCourt tennisCourt;


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

    @Override
    public String toString() {
        return "Timeslot{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", tennisCourt=" + tennisCourt +
                '}';
    }
}
