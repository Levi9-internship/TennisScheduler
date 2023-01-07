package com.tennis.tennisscheduler.repositories;

import com.tennis.tennisscheduler.models.Timeslot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TimeslotRepository extends JpaRepository<Timeslot,Long> {
    @Query("select  t from Timeslot t")
    List<Timeslot> getALlTimeslotsAdmin();
    Optional<Timeslot> findById(long id);
    @Query("select t from Timeslot t join fetch t.person p where p.id = ?2 and t.id <> ?3 and date(t.startDate) = date(?1)")
    Timeslot checkIfTimeslotIsAlreadyReserved(Date startDate, long idPerson, long idTimeslot);
    @Query("select t from Timeslot t join fetch t.person p where p.id = ?1")
    List<Timeslot> getAllTimeslotsForUser(long personId);
    @Query("select t from Timeslot t where date(t.startDate) < date(?1) and date(t.endDate)<date(?1)")
    List<Timeslot> getDeprecatedTimeslots(Date today);
    @Query("select t from Timeslot t join fetch t.tennisCourt court where ?1 <= t.endDate and t.startDate <= ?2 and court.id = ?3 and t.id <> ?4")
    List<Timeslot> checkOverlapping(Date startDate, Date endDate, long idTennisCourt, long idTimeslot);
}
