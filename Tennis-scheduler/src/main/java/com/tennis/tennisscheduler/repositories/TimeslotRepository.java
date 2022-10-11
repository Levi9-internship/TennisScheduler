package com.tennis.tennisscheduler.repositories;

import com.tennis.tennisscheduler.models.Timeslot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeslotRepository extends JpaRepository<Timeslot,Long> {
}
