package com.tennis.tennisscheduler.service;

import com.tennis.tennisscheduler.exception.ApiRequestException;
import com.tennis.tennisscheduler.model.Person;
import com.tennis.tennisscheduler.model.Timeslot;
import com.tennis.tennisscheduler.model.enumes.UserType;
import com.tennis.tennisscheduler.repository.TimeslotRepository;
import com.tennis.tennisscheduler.response.TimeslotResponse;
import com.tennis.tennisscheduler.security.SecurityContextUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class TimeslotService {
    private final TimeslotRepository timeslotRepository;
    private final PersonService personService;
    private final TennisCourtService tennisCourtService;

    public TimeslotResponse update(long id, Timeslot timeslot, BindingResult result){
        Timeslot existingTimeslot = timeslotRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        existingTimeslot.setStartDate(timeslot.getStartDate());
        existingTimeslot.setEndDate(timeslot.getEndDate());
        existingTimeslot.setPerson(personService.findById(timeslot.getPerson().getId()));
        existingTimeslot.setTennisCourt(tennisCourtService.getTennisCourtById(timeslot.getTennisCourt().getId()));
        return reserveTimeslot(existingTimeslot, null);
    }

    public Timeslot getById(Long id){
        return timeslotRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public void deleteById(long id){
        Timeslot existingTimeslot = timeslotRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        if (SecurityContextUtil.GetLoggedUser().getRole().getRoleName().equals(UserType.ROLE_TENNIS_PLAYER) && existingTimeslot.getPerson().getId() != SecurityContextUtil.GetLoggedUser().getId())
            throw new ApiRequestException(HttpStatus.UNAUTHORIZED,"You don't have permission");

        timeslotRepository.deleteById(id);
    }

    @Scheduled(cron = "${greeting.cron}")
    public void deleteDeprecatedTimeslots(){
        for (Timeslot timeslot: timeslotRepository.getDeprecatedTimeslots(new Date())) {
            timeslotRepository.deleteById(timeslot.getId());
        }
    }

    private Timeslot save(Timeslot timeslot) {
        timeslot.setTennisCourt(tennisCourtService.getTennisCourtById(timeslot.getTennisCourt().getId()));
        timeslot.setPerson(personService.findById(timeslot.getPerson().getId()));
        return timeslotRepository.save(timeslot);
    }

    public TimeslotResponse reserveTimeslot(Timeslot timeslot, BindingResult result) {
        Person user = SecurityContextUtil.GetLoggedUser();
        TimeslotResponse timeslotResponse = new TimeslotResponse();

        if (!result.hasErrors() && user.getRole().getRoleName().equals(UserType.ROLE_TENNIS_PLAYER))
            timeslot.setPerson(user);

        if (result.hasErrors())
            throw new ApiRequestException(HttpStatus.BAD_REQUEST,"BAD REQUEST");

        timeslotResponse.setTimeslot(save(timeslot));
        return timeslotResponse;
    }

    public List<Timeslot> getAllTimeslots() {
        if(SecurityContextUtil.GetLoggedUser().getRole().getRoleName().equals(UserType.ROLE_TENNIS_PLAYER))
            return timeslotRepository.getAllTimeslotsForUser(SecurityContextUtil.GetLoggedUser().getId());
        else
            return timeslotRepository.findAll();
    }
}
