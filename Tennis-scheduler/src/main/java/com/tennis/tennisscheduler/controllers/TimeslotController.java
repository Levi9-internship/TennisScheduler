package com.tennis.tennisscheduler.controllers;

import com.tennis.tennisscheduler.dtos.TimeslotDto;
import com.tennis.tennisscheduler.mappers.TimeslotDtoMapper;
import com.tennis.tennisscheduler.models.Timeslot;
import com.tennis.tennisscheduler.services.TimeslotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "timeslots")
public class TimeslotController {

    private final TimeslotService timeslotService;
    private final TimeslotDtoMapper timeslotDtoMapper;

    public TimeslotController(TimeslotService timeslotService, TimeslotDtoMapper timeslotDtoMapper){
        this.timeslotService = timeslotService;
        this.timeslotDtoMapper = timeslotDtoMapper;
    }

    @GetMapping(value = "/")
    public ResponseEntity<List<TimeslotDto>> getAll(){
        List<TimeslotDto> timeslots = new ArrayList<>();
        for (Timeslot timeslot: timeslotService.getAll()) {
            timeslots.add(timeslotDtoMapper.fromTimeslotToTimeslotDto(timeslot));
        }

        return new ResponseEntity<>(timeslots, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TimeslotDto> getById(@PathVariable long id){
        Timeslot timeslot = timeslotService.getById(id);
        if (timeslot == null) {
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(timeslotDtoMapper.fromTimeslotToTimeslotDto(timeslot), HttpStatus.OK);
    }

    @PostMapping(value = "/")
    public ResponseEntity<TimeslotDto> save(@RequestBody TimeslotDto timeslotNew){
        Timeslot timeslot = timeslotService.save(timeslotDtoMapper.fromTimeslotDtoToTimeslot(timeslotNew), timeslotNew.personId, timeslotNew.courtId);

        return new ResponseEntity<>(timeslotDtoMapper.fromTimeslotToTimeslotDto(timeslot), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<TimeslotDto> update(@PathVariable long id, @RequestBody TimeslotDto timeslotUpdate){
        Timeslot timeslotExisting = timeslotService.getById(id);
        if (timeslotExisting == null) {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }

        Timeslot timeslot = timeslotService.update(id, timeslotDtoMapper.fromTimeslotDtoToTimeslot(timeslotUpdate), timeslotUpdate.personId, timeslotUpdate.courtId);
        return new ResponseEntity<>(timeslotDtoMapper.fromTimeslotToTimeslotDto(timeslot), HttpStatus.OK);
    }

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
