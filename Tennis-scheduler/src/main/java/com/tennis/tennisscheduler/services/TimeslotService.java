package com.tennis.tennisscheduler.services;

import com.tennis.tennisscheduler.models.Timeslot;
import com.tennis.tennisscheduler.models.enumes.WorkingHours;
import com.tennis.tennisscheduler.repositories.TimeslotRepository;
import com.tennis.tennisscheduler.response.TimeslotResponse;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Minutes;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
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
        return timeslotRepository.findAll();
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
        if(!checkValidationDate(timeslot))
            timeslotResponse.message = "Selected date must be at same day, and start has to be before end!";
        else if(!checkDate(timeslot))
            timeslotResponse.message = "Selected dates must be in future!";
        else if(!checkDuration(timeslot))
            timeslotResponse.message = "Duration must be between 30 and 120 minutes!";
        else if(!checkWorkingDay(new DateTime(timeslot.getStartDate()), new DateTime(timeslot.getEndDate())))
            timeslotResponse.message = "Working time isn't valid!";
        else if(!checkIfTimeslotIsAlreadyReserved(timeslot))
            timeslotResponse.message = "You can only reserve one timeslot for current day.";
        else if (!checkOverlappingTimeslots(timeslot))
            timeslotResponse.message = "Selected court is not available for selected time.";
        else {
            timeslotResponse.timeslot = save(timeslot);
            timeslotResponse.message = "You successfully reserved timeslot!";
        }
        return timeslotResponse;
    }

    private boolean checkDate(Timeslot timeslot){
        if(timeslot.getStartDate().before(new Date()) || timeslot.getEndDate().before(new Date()) ){
            return false;
        }
        return true;
    }

    private boolean checkValidationDate(Timeslot timeslot){
        if(timeslot.getStartDate().after(timeslot.getEndDate()) || !DateUtils.isSameDay(timeslot.getStartDate(), timeslot.getEndDate())){
            return false;
        }
        return true;
    }

    private boolean checkDuration(Timeslot timeslot){
        Minutes minutes = Minutes.minutesBetween(new DateTime(timeslot.getStartDate()), new DateTime(timeslot.getEndDate()));
        if(minutes.isGreaterThan(Minutes.minutes(120)) || minutes.isLessThan(Minutes.minutes(30))){
            return false;
        }
        return true;
    }

    private boolean checkWorkingDay(DateTime dateStart, DateTime dateEnd){
        if(dateStart.dayOfWeek().get() == DateTimeConstants.SATURDAY || dateStart.dayOfWeek().get() == DateTimeConstants.SUNDAY){
            if(dateStart.getHourOfDay() < WorkingHours.WEEKEND.startHours || dateEnd.getHourOfDay() >= WorkingHours.WEEKEND.endHours){
                return false;
            }
        } else {
            if(dateStart.getHourOfDay() < WorkingHours.WORKING_DAYS.startHours || dateEnd.getHourOfDay() >= WorkingHours.WEEKEND.endHours){
                return false;
            }
        }
        return true;
    }

    private boolean checkOverlappingTimeslots(Timeslot timeslot) {
        if (timeslotRepository.overlappingWithStartOfExistingTimeslot(timeslot.getStartDate(), timeslot.getEndDate(), timeslot.getTennisCourt().getId()).size() > 0)
            return false;
        else if (timeslotRepository.overlappingWithEndOfExistingTimeslot(timeslot.getStartDate(), timeslot.getEndDate(), timeslot.getTennisCourt().getId()).size() > 0)
            return false;
        else if (timeslotRepository.overlappingWithStartAndEndOfExistingTimeslot(timeslot.getStartDate(), timeslot.getEndDate(), timeslot.getTennisCourt().getId()).size() > 0)
            return false;
        else if (timeslotRepository.overlappingWithMiddleOfExistingTimeslot(timeslot.getStartDate(), timeslot.getEndDate(), timeslot.getTennisCourt().getId()).size() > 0)
            return false;
        else
            return true;
    }

    private boolean checkIfTimeslotIsAlreadyReserved(Timeslot timeslot){
        if (timeslotRepository.checkIfTimeslotIsAlreadyReserved(timeslot.getStartDate(),timeslot.getPerson().getId()) == null)
            return true;
        else
            return false;
    }
}
