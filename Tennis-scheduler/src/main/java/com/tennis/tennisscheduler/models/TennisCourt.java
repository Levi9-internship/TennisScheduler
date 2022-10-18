package com.tennis.tennisscheduler.models;

import com.tennis.tennisscheduler.models.enumes.SurfaceType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Setter
@Getter
@Table
public class TennisCourt {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column
    private long id;
    @Column(unique = true)
    private String name;
    @Column
    private SurfaceType surfaceType;
    @Column
    private String description;
    @Column
    private String image;
    @OneToMany(mappedBy = "tennisCourt", fetch = FetchType.LAZY,cascade =  CascadeType.MERGE)
    private Set<Timeslot> timeslot;
    @OneToOne(fetch = FetchType.EAGER,cascade =  CascadeType.ALL)
    private Address address;
}