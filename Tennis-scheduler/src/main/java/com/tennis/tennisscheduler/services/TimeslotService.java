package com.tennis.tennisscheduler.services;

import com.tennis.tennisscheduler.models.Person;
import com.tennis.tennisscheduler.models.Timeslot;
import com.tennis.tennisscheduler.repositories.TimeslotRepository;
import com.tennis.tennisscheduler.response.TimeslotResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TimeslotService {
    private final TimeslotRepository timeslotRepository;
    private final PersonService personService;
    private final TennisCourtServices tennisCourtService;

    public TimeslotResponse update(long id, Timeslot timeslot){
        Timeslot existingTimeslot = timeslotRepository.findById(id);
        existingTimeslot.setStartDate(timeslot.getStartDate());
        existingTimeslot.setEndDate(timeslot.getEndDate());
        existingTimeslot.setPerson(personService.findById(timeslot.getPerson().getId()));
        existingTimeslot.setTennisCourt(tennisCourtService.getTennisCourtById(timeslot.getTennisCourt().getId()));
        return reserveTimeslot(existingTimeslot);
    }

    public List<Timeslot> getAll(){
        return timeslotRepository.getALlTimeslotsAdmin();
    }

    public List<Timeslot> getAllTimeslotsForUser(long personId){
        return timeslotRepository.getAllTimeslotsForUser(personId);
    }

    public Timeslot getById(long id){
        return timeslotRepository.findById(id);
    }

    public void deleteById(long id){
        timeslotRepository.deleteById(id);
    }

    public Timeslot save(Timeslot timeslot) {
        timeslot.setTennisCourt(tennisCourtService.getTennisCourtById(timeslot.getTennisCourt().getId()));
        timeslot.setPerson(personService.findById(timeslot.getPerson().getId()));
        return timeslotRepository.save(timeslot);
    }

    public TimeslotResponse reserveTimeslot(Timeslot timeslot) {
        TimeslotResponse timeslotResponse = new TimeslotResponse();
        timeslotResponse.setTimeslot(save(timeslot));
        return timeslotResponse;
    }

    public void cancelTimeslot(long idTimeslot) {
        Timeslot existingTimeslot = timeslotRepository.findById(idTimeslot);
        existingTimeslot.setDeleted(true);
        timeslotRepository.save(existingTimeslot);
    }
}
