package com.tennis.tennisscheduler.services;

import com.tennis.tennisscheduler.models.Timeslot;
import com.tennis.tennisscheduler.repositories.TimeslotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeslotService {
    private final TimeslotRepository timeslotRepository;
    private final PersonService personService;

    public TimeslotService(TimeslotRepository timeslotRepository, PersonService personService){
        this.timeslotRepository = timeslotRepository;
        this.personService = personService;
    }

    public Timeslot update(long id, Timeslot timeslot, long personId, long courtId){
        Timeslot existingTimeslot = timeslotRepository.findById(id);

        existingTimeslot.setDuration(timeslot.getDuration());
        existingTimeslot.setStartDate(timeslot.getStartDate());
        existingTimeslot.setEndDate(timeslot.getEndDate());
        existingTimeslot.setPerson(this.personService.findById(personId));
        //set tennis court
        return timeslotRepository.save(existingTimeslot);
    }

    public List<Timeslot> getAll(){
        return timeslotRepository.findAll();
    }

    public Timeslot getById(long id){
        return timeslotRepository.findById(id);
    }

    public void deleteById(long id){
        timeslotRepository.deleteById(id);
    }

    public Timeslot save(Timeslot timeslotNew, long personId, long courtId) {
        //set tennis court
        timeslotNew.setPerson(personService.findById(personId));
        return timeslotRepository.save(timeslotNew);
    }
}
