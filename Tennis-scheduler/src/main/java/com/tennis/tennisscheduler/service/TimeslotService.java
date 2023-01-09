package com.tennis.tennisscheduler.service;

import com.tennis.tennisscheduler.model.Timeslot;
import com.tennis.tennisscheduler.repository.TimeslotRepository;
import com.tennis.tennisscheduler.response.TimeslotResponse;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TimeslotService {
    private final TimeslotRepository timeslotRepository;
    private final PersonService personService;
    private final TennisCourtService tennisCourtService;

    public TimeslotResponse update(long id, Timeslot timeslot){
        Timeslot existingTimeslot = timeslotRepository.findById(id).get();
        existingTimeslot.setStartDate(timeslot.getStartDate());
        existingTimeslot.setEndDate(timeslot.getEndDate());
        existingTimeslot.setPerson(personService.findById(timeslot.getPerson().getId()).get());
        existingTimeslot.setTennisCourt(tennisCourtService.getTennisCourtById(timeslot.getTennisCourt().getId()).get());
        return reserveTimeslot(existingTimeslot);
    }

    public List<Timeslot> getAll(){
        return timeslotRepository.getALlTimeslotsAdmin();
    }

    public List<Timeslot> getAllTimeslotsForUser(long personId){
        return timeslotRepository.getAllTimeslotsForUser(personId);
    }

    public Optional<Timeslot> getById(long id){
        return timeslotRepository.findById(id);
    }

    public void deleteById(long id){
        timeslotRepository.deleteById(id);
    }

    @Scheduled(cron = "${greeting.cron}")
    public void deleteDeprecatedTimeslots(){
        for (Timeslot timeslot: timeslotRepository.getDeprecatedTimeslots(new Date())) {
            timeslotRepository.deleteById(timeslot.getId());
        }
    }

    public Timeslot save(Timeslot timeslot) {
        timeslot.setTennisCourt(tennisCourtService.getTennisCourtById(timeslot.getTennisCourt().getId()).get());
        timeslot.setPerson(personService.findById(timeslot.getPerson().getId()).get());
        return timeslotRepository.save(timeslot);
    }

    public TimeslotResponse reserveTimeslot(Timeslot timeslot) {
        TimeslotResponse timeslotResponse = new TimeslotResponse();
        timeslotResponse.setTimeslot(save(timeslot));
        return timeslotResponse;
    }
}
