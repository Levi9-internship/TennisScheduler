package com.tennis.tennisscheduler.repositories;

import com.tennis.tennisscheduler.models.Person;
import com.tennis.tennisscheduler.models.enumes.Gender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class PersonRepositoryTest {

    @Autowired
    PersonRepository personRepository;

    @AfterEach
    void tearDown() {
        personRepository.deleteAll();
    }
    Date date_s = new Date();
    @Test
    void findById() {

        Person person = Person.builder()
                .firstName("Zoran")
                .lastName("Zoranovic")
                .email("zoran@gmail.com")
                .birthday(date_s)
                .gender(Gender.MALE)
                .address(null)
                .phoneNumber("0621234567")
                .build();

        Person savedPerson = personRepository.save(person);
        //when
        Person expected = personRepository.findById(savedPerson.getId()).get();
        //then
        assertThat(expected).isEqualTo(savedPerson);
    }
}