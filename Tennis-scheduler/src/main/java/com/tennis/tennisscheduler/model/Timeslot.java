package com.tennis.tennisscheduler.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Timeslot {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private Date startDate;
    private Date endDate;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="person_id")
    @JsonIgnore
    private Person person;
    @ManyToOne
    @JoinColumn(name="tennis_court_id")
    @JsonIgnore
    private TennisCourt tennisCourt;
}
