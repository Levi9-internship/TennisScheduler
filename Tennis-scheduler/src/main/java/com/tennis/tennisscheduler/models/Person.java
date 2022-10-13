package com.tennis.tennisscheduler.models;

import com.tennis.tennisscheduler.models.enumes.Gender;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;


@Entity
@Table
public class Person {

    @Id
    @SequenceGenerator(name = "personSeqGen", sequenceName = "personSeqGen", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "personSeqGen")
    @Column(name="id", unique=true, nullable=false)
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
    @OneToOne(fetch = FetchType.EAGER,cascade =  CascadeType.MERGE)
    private Address address;

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY,cascade =  CascadeType.MERGE)
    private Set<Timeslot> timeslot;


    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Timeslot> getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(Set<Timeslot> timeslot) {
        this.timeslot = timeslot;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
