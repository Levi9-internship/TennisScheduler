package com.tennis.tennisscheduler.services;

import com.tennis.tennisscheduler.dtos.TimeslotNewDto;
import com.tennis.tennisscheduler.models.Timeslot;
import com.tennis.tennisscheduler.repositories.TimeslotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeslotService {
    @Autowired
    private TimeslotRepository timeslotRepository;
    @Autowired
    private PersonService personService;

    public Timeslot update(long id, TimeslotNewDto timeslot){
        Timeslot existingTimeslot = this.timeslotRepository.findById(id);

        existingTimeslot.setDuration(timeslot.duration);
        existingTimeslot.setStartDate(timeslot.dateStart);
        existingTimeslot.setEndDate(timeslot.dateEnd);
        existingTimeslot.setPerson(this.personService.findById(timeslot.personId));
        existingTimeslot.setTennisCourt(null); //add tennis court
        return timeslotRepository.save(existingTimeslot);
    }

    public List<Timeslot> getAll(){
        return this.timeslotRepository.findAll();
    }

    public Timeslot getById(long id){
        return this.timeslotRepository.findById(id);
    }

    public void deleteById(long id){
        this.timeslotRepository.deleteById(id);
    }

    public Timeslot save(TimeslotNewDto timeslotNew) {
        //add tennis court
        return this.timeslotRepository.save(new Timeslot(timeslotNew.dateStart, timeslotNew.dateEnd, timeslotNew.duration, personService.findById(timeslotNew.personId), null));
    }
}
