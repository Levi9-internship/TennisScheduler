package com.tennis.tennisscheduler.repository;

import com.tennis.tennisscheduler.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person,Long> {
    Person findByEmail(String email);
}
