package com.tennis.tennisscheduler.services;

import com.tennis.tennisscheduler.models.Timeslot;
import com.tennis.tennisscheduler.repositories.TimeslotRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Minutes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
        existingTimeslot.setStartDate(timeslot.getStartDate());
        existingTimeslot.setEndDate(timeslot.getEndDate());
        existingTimeslot.setPerson(this.personService.findById(personId));
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

    public String checkTimeslotValidity(Timeslot timeslot) {
        if(checkValidationDate(timeslot)){
            if(checkDate(timeslot)){
                if(checkDuration(timeslot)){
                    if(checkWorkingDay(new DateTime(timeslot.getStartDate()), new DateTime(timeslot.getEndDate()))){

                        return "You can reserve only one timeslot for current day!";
                    }
                    return "Working time isn't valid!";
                }
                return "Duration must be between 30 and 120 minutes!";
            }
            return  "Selected dates must be in future!";
        }
        return "Selected date must be at same day, and start has to be before end!";
    }

    public boolean checkDate(Timeslot timeslot){
        if(timeslot.getStartDate().before(new Date()) || timeslot.getEndDate().before(new Date()) ){
            return false;
        }
        return true;
    }

    public boolean checkValidationDate(Timeslot timeslot){
        if(timeslot.getStartDate().after(timeslot.getEndDate()) || !DateUtils.isSameDay(timeslot.getStartDate(), timeslot.getEndDate())){
            return false;
        }
        return true;
    }

    public boolean checkDuration(Timeslot timeslot){
        Minutes minutes = Minutes.minutesBetween(new DateTime(timeslot.getStartDate()), new DateTime(timeslot.getEndDate()));
        if(minutes.isGreaterThan(Minutes.minutes(120)) || minutes.isLessThan(Minutes.minutes(30))){
            return false;
        }
        return true;
    }

    public boolean checkWorkingDay(DateTime dateStart, DateTime dateEnd){
        if(dateStart.dayOfWeek().get() == DateTimeConstants.SATURDAY || dateStart.dayOfWeek().get() == DateTimeConstants.SUNDAY){
            if(dateStart.getHourOfDay() < 17 || dateEnd.getHourOfDay() > 22){
                return false;
            }
        } else {
            if(dateStart.getHourOfDay() < 18 || dateEnd.getHourOfDay() > 23){
                return false;
            }
        }
        return true;
    }
}
