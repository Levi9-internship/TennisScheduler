package com.tennis.tennisscheduler.dto;

import com.tennis.tennisscheduler.model.Address;
import com.tennis.tennisscheduler.model.enumes.Gender;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PersonDto {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private Gender gender;
    private String phoneNumber;
    private Date birthday;
    private Address address;
    private String password;
}
