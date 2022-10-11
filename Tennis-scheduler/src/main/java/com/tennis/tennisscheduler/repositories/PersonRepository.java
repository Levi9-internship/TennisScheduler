package com.tennis.tennisscheduler.repositories;

import com.tennis.tennisscheduler.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PersonRepository extends JpaRepository<Person,Long> {

    Person findById(long id);
}
