package com.tennis.tennisscheduler.controllers;

import com.tennis.tennisscheduler.dtos.TimeslotDto;
import com.tennis.tennisscheduler.dtos.TimeslotResponseDto;
import com.tennis.tennisscheduler.mappers.TimeslotDtoMapper;
import com.tennis.tennisscheduler.mappers.TimeslotResponseDtoMapper;
import com.tennis.tennisscheduler.models.Timeslot;
import com.tennis.tennisscheduler.services.TimeslotService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "timeslots")
@AllArgsConstructor
@SecurityRequirement(name = "Authorization")
public class TimeslotController {
    private final TimeslotService timeslotService;
    private final TimeslotDtoMapper timeslotDtoMapper;
    private final TimeslotResponseDtoMapper timeslotResponseDtoMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/")
    public ResponseEntity<List<TimeslotDto>> getAll(){
        List<TimeslotDto> timeslots = new ArrayList<>();
        for (Timeslot timeslot: timeslotService.getAll()) {
            timeslots.add(timeslotDtoMapper.fromTimeslotToTimeslotDto(timeslot));
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
        TimeslotResponseDto timeslotResponse = new TimeslotResponseDto();
        if (!result.hasErrors()){
            timeslotResponse = timeslotResponseDtoMapper.toTimeslotResponseDto(timeslotService.reserveTimeslot(timeslotDtoMapper.fromTimeslotDtoToTimeslot(timeslotNew)));
            return new ResponseEntity<>(timeslotResponse, HttpStatus.CREATED);
        }
        else {
            timeslotResponse.message = result.getAllErrors();
            return new ResponseEntity<>(timeslotResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','TENNIS_PLAYER')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<TimeslotResponseDto> update(@PathVariable long id,@Valid @RequestBody  TimeslotDto timeslotUpdate, BindingResult result){
        TimeslotResponseDto timeslotResponse = new TimeslotResponseDto();
        if (!result.hasErrors()){
            timeslotResponse = timeslotResponseDtoMapper.toTimeslotResponseDto(timeslotService.update(id, timeslotDtoMapper.fromTimeslotDtoToTimeslot(timeslotUpdate)));
            return new ResponseEntity<>(timeslotResponse, HttpStatus.CREATED);
        }
        else {
            timeslotResponse.message = result.getAllErrors();
            return new ResponseEntity<>(timeslotResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable long id){
        Timeslot timeslotExisting = timeslotService.getById(id);
        if (timeslotExisting == null) {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }
        timeslotService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
