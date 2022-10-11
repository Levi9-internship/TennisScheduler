package com.tennis.tennisscheduler.services;

import com.tennis.tennisscheduler.models.Person;
import com.tennis.tennisscheduler.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Person findById(long id){
        return personRepository.findById(id);
    }


}
