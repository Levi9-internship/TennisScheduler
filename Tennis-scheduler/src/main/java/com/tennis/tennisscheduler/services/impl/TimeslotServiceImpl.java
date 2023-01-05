package com.tennis.tennisscheduler.services.impl;

import com.tennis.tennisscheduler.models.Timeslot;
import com.tennis.tennisscheduler.repositories.TimeslotRepository;
import com.tennis.tennisscheduler.response.TimeslotResponse;
import com.tennis.tennisscheduler.services.TimeslotService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TimeslotServiceImpl implements TimeslotService {
    private final TimeslotRepository timeslotRepository;
    private final PersonServiceImpl personServiceImpl;
    private final TennisCourtServiceImpl tennisCourtServiceImpl;

    @Override
    public TimeslotResponse update(long id, Timeslot timeslot){
        Timeslot existingTimeslot = timeslotRepository.findById(id).get();
        existingTimeslot.setStartDate(timeslot.getStartDate());
        existingTimeslot.setEndDate(timeslot.getEndDate());
        existingTimeslot.setPerson(personServiceImpl.findById(timeslot.getPerson().getId()).get());
        existingTimeslot.setTennisCourt(tennisCourtServiceImpl.getTennisCourtById(timeslot.getTennisCourt().getId()).get());
        return reserveTimeslot(existingTimeslot);
    }

    @Override
    public List<Timeslot> getAll(){
        return timeslotRepository.getALlTimeslotsAdmin();
    }

    @Override
    public List<Timeslot> getAllTimeslotsForUser(long personId){
        return timeslotRepository.getAllTimeslotsForUser(personId);
    }

    @Override
    public Optional<Timeslot> getById(long id){
        return timeslotRepository.findById(id);
    }

    @Override
    public void deleteById(long id){
        timeslotRepository.deleteById(id);
    }

    @Override
    @Scheduled(cron = "${greeting.cron}")
    public void deleteDeprecatedTimeslots(){
        for (Timeslot timeslot: timeslotRepository.getDeprecatedTimeslots(new Date())) {
            timeslotRepository.deleteById(timeslot.getId());
        }
    }

    @Override
    public Timeslot save(Timeslot timeslot) {
        timeslot.setTennisCourt(tennisCourtServiceImpl.getTennisCourtById(timeslot.getTennisCourt().getId()).get());
        timeslot.setPerson(personServiceImpl.findById(timeslot.getPerson().getId()).get());
        return timeslotRepository.save(timeslot);
    }

    @Override
    public TimeslotResponse reserveTimeslot(Timeslot timeslot) {
        TimeslotResponse timeslotResponse = new TimeslotResponse();
        timeslotResponse.setTimeslot(save(timeslot));
        return timeslotResponse;
    }
}
