package com.tennis.tennisscheduler.dtos;

import com.tennis.tennisscheduler.models.Address;
import com.tennis.tennisscheduler.models.enumes.Gender;
import lombok.Data;

import java.util.Date;

public class PersonDto {

    public long id;
    public String firstName;
    public String lastName;
    public String email;
    public Gender gender;
    public String phoneNumber;
    public Date birthday;
    public Address address;
}
