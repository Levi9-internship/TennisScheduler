package com.tennis.tennisscheduler.repositories;

import com.tennis.tennisscheduler.models.Timeslot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Repository
public interface TimeslotRepository extends JpaRepository<Timeslot,Long> {
    Timeslot findById(long id);
    @Query("select t from Timeslot t join fetch t.person p where p.id = ?2 and date(t.startDate) = date(?1)")
    Timeslot checkIfTimeslotIsAlreadyReserved(Date startDate, long idPerson);
    //  |***timeslot***|
    //      |---existingTimeslot---|
    @Query("select t from Timeslot t where ?1 <= t.startDate and ?2 <= t.endDate and t.startDate >= ?1 and t.startDate <= ?2")
    List<Timeslot> overlappingWithStartOfExistingTimeslot(Date startDate, Date endDate);
    //                  |***timeslot***|
    //  |---existingTimeslot---|
    @Query("select t from Timeslot t where ?1 >= t.startDate and ?2 >= t.endDate and t.endDate >= ?1 and t.endDate <= ?2")
    List<Timeslot> overlappingWithEndOfExistingTimeslot(Date startDate, Date endDate);
    //  |*********timeslot*********|
    //    |--existingTimeslot--|
    @Query("select t from Timeslot t where ?1 <= t.startDate and ?2 >= t.endDate")
    List<Timeslot> overlappingWithStartAndEndOfExistingTimeslot(Date startDate, Date endDate);
    //       |***timeslot***|
    //  |----existingTimeslot----|
    @Query("select t from Timeslot t where ?1 >= t.startDate and ?2 <= t.endDate")
    List<Timeslot> overlappingWithMiddleOfExistingTimeslot(Date startDate, Date endDate);
    @Query("select t from Timeslot t join fetch t.tennisCourt court where court.id = ?1 and date(t.startDate) = date(?2)")
    List<Timeslot> checkIfTennisCourtIsAvailable(long id, Date startDate);
}
