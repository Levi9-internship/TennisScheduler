package com.tennis.tennisscheduler.repositories;

import com.tennis.tennisscheduler.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person,Long> {
}
