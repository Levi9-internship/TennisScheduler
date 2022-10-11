package com.tennis.tennisscheduler.repositories;

import com.tennis.tennisscheduler.models.Timeslot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeslotRepository extends JpaRepository<Timeslot,Long> {
    @Query(value = "select t from Timeslot t join fetch t.person p join fetch t.tennisCourt c where t.id = ?1")
    Timeslot getFullTimeslotById(Long id);
}
