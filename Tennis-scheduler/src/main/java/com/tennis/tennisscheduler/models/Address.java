package com.tennis.tennisscheduler.models;

import javax.persistence.*;

@Entity
@Table
public class Address {

    @Id
    @SequenceGenerator(name = "addressSeqGen", sequenceName = "addressSeqGen", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "addressSeqGen")
    @Column(name="id", unique=true, nullable=false)
    private Long id;
    @Column
    private String country;
    @Column
    private String city;
    @Column
    private String street;
    @Column
    private int number;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
