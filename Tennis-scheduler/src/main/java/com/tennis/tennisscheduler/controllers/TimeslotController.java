package com.tennis.tennisscheduler.controllers;

import com.tennis.tennisscheduler.dtos.TimeslotDto;
import com.tennis.tennisscheduler.dtos.TimeslotResponseDto;
import com.tennis.tennisscheduler.mappers.TimeslotDtoMapper;
import com.tennis.tennisscheduler.mappers.TimeslotResponseDtoMapper;
import com.tennis.tennisscheduler.models.Person;
import com.tennis.tennisscheduler.models.Timeslot;
import com.tennis.tennisscheduler.models.enumes.UserType;
import com.tennis.tennisscheduler.services.TimeslotService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "timeslots")
@AllArgsConstructor
@SecurityRequirement(name = "Authorization")
public class TimeslotController {
    private final TimeslotService timeslotService;
    private final TimeslotDtoMapper timeslotDtoMapper;
    private final TimeslotResponseDtoMapper timeslotResponseDtoMapper;

    @PreAuthorize("hasAnyRole('ADMIN','TENNIS_PLAYER')")
    @GetMapping(value = "/")
    public ResponseEntity<List<TimeslotDto>> getAll(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person user = (Person)authentication.getPrincipal();

        List<TimeslotDto> timeslots = new ArrayList<>();
        if(user.getRole().getRoleName().equals(UserType.ROLE_TENNIS_PLAYER)) {
            for (Timeslot timeslot : timeslotService.getAllTimeslotsForUser(user.getId())) {
                timeslots.add(timeslotDtoMapper.fromTimeslotToTimeslotDto(timeslot));
            }
        } else{
            for (Timeslot timeslot : timeslotService.getAll()) {
                timeslots.add(timeslotDtoMapper.fromTimeslotToTimeslotDto(timeslot));
            }
        }
        return new ResponseEntity<>(timeslots, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','TENNIS_PLAYER')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<TimeslotDto> getById(@PathVariable long id){
        Timeslot timeslot = timeslotService.getById(id);
        if (timeslot == null) {
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(timeslotDtoMapper.fromTimeslotToTimeslotDto(timeslot), HttpStatus.OK);
    }


    @PreAuthorize("hasAnyRole('ADMIN','TENNIS_PLAYER')")
    @PostMapping(value = "/")
    public ResponseEntity<TimeslotResponseDto> save(@RequestBody @Valid TimeslotDto timeslotNew, BindingResult result){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person user = (Person)authentication.getPrincipal();

        TimeslotResponseDto timeslotResponse = new TimeslotResponseDto();
        if (!result.hasErrors() && user.getRole().getRoleName().equals(UserType.ROLE_TENNIS_PLAYER)){
            timeslotNew.personId = user.getId();
            timeslotResponse = timeslotResponseDtoMapper.toTimeslotResponseDto(timeslotService.reserveTimeslot(timeslotDtoMapper.fromTimeslotDtoToTimeslot(timeslotNew)));
            return new ResponseEntity<>(timeslotResponse, HttpStatus.CREATED);
        }
        else if(!result.hasErrors() && user.getRole().getRoleName().equals(UserType.ROLE_ADMIN)){
            timeslotResponse = timeslotResponseDtoMapper.toTimeslotResponseDto(timeslotService.reserveTimeslot(timeslotDtoMapper.fromTimeslotDtoToTimeslot(timeslotNew)));
            return new ResponseEntity<>(timeslotResponse, HttpStatus.CREATED);
        } else {
            timeslotResponse.message = result.getAllErrors();
            return new ResponseEntity<>(timeslotResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','TENNIS_PLAYER')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<TimeslotResponseDto> update(@PathVariable long id, @RequestBody @Valid TimeslotDto timeslotUpdate){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person user = (Person)authentication.getPrincipal();

        Timeslot timeslotExisting = timeslotService.getById(id);
        if (timeslotExisting == null)
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);

        if(user.getRole().getRoleName().equals(UserType.ROLE_TENNIS_PLAYER)){
            if(timeslotExisting.getPerson().getId()== user.getId()){
                if (timeslotService.checkIfUserIsOwnerOfTimeslot(user.getId(), timeslotUpdate.id)){
                    timeslotUpdate.personId = user.getId();
                    TimeslotResponseDto timeslotResponse = timeslotResponseDtoMapper.toTimeslotResponseDto(timeslotService.update(id, timeslotDtoMapper.fromTimeslotDtoToTimeslot(timeslotUpdate)));
                    if(timeslotResponse.timeslot != null)
                        return new ResponseEntity<>(timeslotResponse, HttpStatus.OK);
                    else
                        return new ResponseEntity<>(timeslotResponse, HttpStatus.BAD_REQUEST);
                } else
                    return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            } else
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        TimeslotResponseDto timeslotResponse = timeslotResponseDtoMapper.toTimeslotResponseDto(timeslotService.update(id, timeslotDtoMapper.fromTimeslotDtoToTimeslot(timeslotUpdate)));
        if(timeslotResponse.timeslot != null)
            return new ResponseEntity<>(timeslotResponse, HttpStatus.OK);
        else
            return new ResponseEntity<>(timeslotResponse, HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable long id){
        Timeslot timeslotExisting = timeslotService.getById(id);
        if (timeslotExisting == null) {
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        }
        timeslotService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('ADMIN','TENNIS_PLAYER')")
    @PutMapping(value = "/cancel/{id}")
    public ResponseEntity<HttpStatus> cancelTimeslot(@PathVariable long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person user = (Person)authentication.getPrincipal();

        Timeslot timeslotExisting = timeslotService.getById(id);
        if (timeslotExisting == null)
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);

        if (user.getRole().getRoleName().equals(UserType.ROLE_TENNIS_PLAYER) && timeslotExisting.getPerson().getId() != user.getId())
            return new ResponseEntity<>( HttpStatus.UNAUTHORIZED);

        if (user.getRole().getRoleName().equals(UserType.ROLE_TENNIS_PLAYER))
            timeslotService.cancelTimeslot(user, id);
        else
            timeslotService.cancelTimeslot(timeslotExisting.getPerson(), id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
