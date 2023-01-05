package com.tennis.tennisscheduler.services;

import com.tennis.tennisscheduler.models.Timeslot;
import com.tennis.tennisscheduler.response.TimeslotResponse;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TimeslotService {
    TimeslotResponse update(long id, Timeslot timeslot);

    List<Timeslot> getAll();

    List<Timeslot> getAllTimeslotsForUser(long personId);

   Optional<Timeslot> getById(long id);

    void deleteById(long id);

    @Scheduled(cron = "${greeting.cron}")
    void deleteDeprecatedTimeslots();

    Timeslot save(Timeslot timeslot);

    TimeslotResponse reserveTimeslot(Timeslot timeslot);
}
