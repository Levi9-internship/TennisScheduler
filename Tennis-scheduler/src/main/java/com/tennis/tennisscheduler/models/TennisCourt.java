package com.tennis.tennisscheduler.models;

import com.tennis.tennisscheduler.models.enumes.SurfaceType;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class TennisCourt {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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
    @OneToOne(fetch = FetchType.EAGER,cascade =  CascadeType.PERSIST)
    private Address address;

    public void setId(long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Set<Timeslot> getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(Set<Timeslot> timeslot) {
        this.timeslot = timeslot;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public SurfaceType getSurfaceType() {
        return surfaceType;
    }

    public void setSurfaceType(SurfaceType surfaceType) {
        this.surfaceType = surfaceType;
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }
}
