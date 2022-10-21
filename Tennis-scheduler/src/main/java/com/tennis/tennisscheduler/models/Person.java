package com.tennis.tennisscheduler.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.tennis.tennisscheduler.models.enumes.Gender;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;


@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column
    private long id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column(unique = true)
    private String email;
    @Column
    private Gender gender;
    @Column
    private String phoneNumber;
    @Column
    private Date birthday;
    @OneToOne(fetch = FetchType.EAGER,cascade =  CascadeType.ALL)
    private Address address;

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY,cascade =  CascadeType.PERSIST)
    private Set<Timeslot> timeslot;

}
