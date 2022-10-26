package com.tennis.tennisscheduler.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table
public class Timeslot {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column
    private long id;
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

    @Column
    private boolean deleted = false;
}
