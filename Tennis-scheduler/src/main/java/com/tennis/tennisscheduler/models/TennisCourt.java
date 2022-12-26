package com.tennis.tennisscheduler.models;

import com.tennis.tennisscheduler.models.enumes.SurfaceType;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    @OneToMany(mappedBy = "tennisCourt", fetch = FetchType.LAZY,cascade =  CascadeType.ALL)
    private Set<Timeslot> timeslot;
    @OneToOne(fetch = FetchType.EAGER,cascade =  CascadeType.ALL)
    private Address address;
    @ManyToOne(fetch = FetchType.EAGER,cascade =  CascadeType.PERSIST)
    @JoinColumn(name="working_time_id")
    private WorkingTime workingTime;
}