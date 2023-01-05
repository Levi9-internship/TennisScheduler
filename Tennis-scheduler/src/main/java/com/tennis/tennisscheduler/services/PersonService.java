package com.tennis.tennisscheduler.services;

import com.tennis.tennisscheduler.models.Person;
import com.tennis.tennisscheduler.models.enumes.UserType;

import java.util.List;
import java.util.Optional;

public interface PersonService {

    List<Person> getAllPersons();

    Optional<Person> findById(long id);

    Person savePerson(Person person);

    void deletePersonById(long id);

    Person updatePerson(long id,Person person);


    Boolean updatePassword(long id, String oldPassword, String newPassword);

}
