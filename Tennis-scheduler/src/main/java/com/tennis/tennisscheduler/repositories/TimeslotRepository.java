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
    @Query("select t from Timeslot t join fetch t.person p where p.id = ?2 and t.id = ?1")
    Timeslot findTimeSlotUserById(long id,long userId);
    @Query("select t from Timeslot t join fetch t.person p where p.id = ?2 and date(t.startDate) = date(?1) and t.deleted = false")
    Timeslot checkIfTimeslotIsAlreadyReserved(Date startDate, long idPerson);
    @Query("select t from Timeslot t join fetch t.tennisCourt court where ?1 <= t.startDate and ?2 <= t.endDate and t.startDate >= ?1 and t.startDate <= ?2 and court.id = ?3 and t.deleted = false")
    List<Timeslot> overlappingWithStartOfExistingTimeslot(Date startDate, Date endDate, long idTennisCourt);
    @Query("select t from Timeslot t join fetch t.tennisCourt court where ?1 >= t.startDate and ?2 >= t.endDate and t.endDate >= ?1 and t.endDate <= ?2 and court.id = ?3 and t.deleted = false")
    List<Timeslot> overlappingWithEndOfExistingTimeslot(Date startDate, Date endDate, long idTennisCourt);
    @Query("select t from Timeslot t join fetch t.tennisCourt court where ?1 <= t.startDate and ?2 >= t.endDate and court.id = ?3 and t.deleted = false")
    List<Timeslot> overlappingWithStartAndEndOfExistingTimeslot(Date startDate, Date endDate, long idTennisCourt);
    @Query("select t from Timeslot t join fetch t.tennisCourt court where ?1 >= t.startDate and ?2 <= t.endDate and court.id = ?3 and t.deleted = false")
    List<Timeslot> overlappingWithMiddleOfExistingTimeslot(Date startDate, Date endDate, long idTennisCourt);
    @Query("select t from Timeslot t join fetch t.tennisCourt court where court.id = ?1 and date(t.startDate) = date(?2) and t.deleted = false")
    List<Timeslot> checkIfTennisCourtIsAvailable(long idTennisCourt, Date startDate);
    @Query("select t from Timeslot t join fetch t.person p where t.id = ?2 and p.id = ?1")
    Timeslot checkIfUserIsOwnerOfTimeslot(long userId, long timeslotId);
    @Query("select t from Timeslot t join fetch t.person p where p.id = ?1 and t.deleted = false")
    List<Timeslot> getAllTimeslotsForUser(long personId);
}
