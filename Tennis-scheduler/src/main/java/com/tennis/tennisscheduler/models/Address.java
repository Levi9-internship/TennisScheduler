package com.tennis.tennisscheduler.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id", unique=true, nullable=false)
    private long id;
    @Column
    private String country;
    @Column
    private String city;
    @Column
    private String street;
    @Column
    private int number;

}
