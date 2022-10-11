package com.tennis.tennisscheduler.services;

import com.tennis.tennisscheduler.dtos.TimeslotDto;
import com.tennis.tennisscheduler.dtos.TimeslotNewDto;
import com.tennis.tennisscheduler.models.Timeslot;
import com.tennis.tennisscheduler.repositories.TimeslotRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class TimeslotService {
    @Autowired
    private TimeslotRepository timeslotRepository;

    @Autowired
    private PersonService personService;

    public Timeslot save(Timeslot timeslot){
        return timeslotRepository.save(timeslot);
    }

    public Timeslot update(Timeslot timeslot){
        Timeslot existingTimeslot = this.timeslotRepository.getFullTimeslotById(timeslot.getId());
        existingTimeslot.setDuration(timeslot.getDuration());
        existingTimeslot.setStartDate(timeslot.getStartDate());
        existingTimeslot.setEndDate(timeslot.getEndDate());
        existingTimeslot.setPerson(timeslot.getPerson());
        existingTimeslot.setTennisCourt(timeslot.getTennisCourt());
        return timeslotRepository.save(timeslot);
    }

    public List<Timeslot> getAll(){
        return this.timeslotRepository.findAll();
    }

    public Timeslot getById(Long id){
        return this.timeslotRepository.getById(id);
    }

    public void deleteById(Long id){
        this.timeslotRepository.deleteById(id);
    }

    public Timeslot saveDto(TimeslotNewDto timeslotNew) {
        //izmeniti
        return this.timeslotRepository.save(new Timeslot(timeslotNew.id, timeslotNew.dateStart, timeslotNew.dateEnd, timeslotNew.duration, personService.findById(timeslotNew.personId), null));
    }
}
