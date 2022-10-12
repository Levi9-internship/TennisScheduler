package com.tennis.tennisscheduler.controllers;

import com.tennis.tennisscheduler.dtos.TimeslotDto;
import com.tennis.tennisscheduler.dtos.TimeslotNewDto;
import com.tennis.tennisscheduler.mappers.TimeslotDtoMapper;
import com.tennis.tennisscheduler.models.Timeslot;
import com.tennis.tennisscheduler.services.TimeslotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "timeslot")
public class TimeslotController {

    @Autowired
    private TimeslotService timeslotService;

    @Autowired
    private TimeslotDtoMapper timeslotDtoMapper;

    @GetMapping
    public ResponseEntity<List<TimeslotDto>> getAll(){
        List<TimeslotDto> timeslots = new ArrayList<>();
        for (Timeslot timeslot: timeslotService.getAll()) {
            timeslots.add(timeslotDtoMapper.fromTimeslotToTimeslotDto(timeslot));
        }

        return new ResponseEntity<>(timeslots, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TimeslotDto> getById(@PathVariable long id){
        Timeslot timeslot = this.timeslotService.getById(id);
        if (timeslot == null) {
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(timeslotDtoMapper.fromTimeslotToTimeslotDto(timeslot), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TimeslotDto> save(@RequestBody TimeslotNewDto timeslotNew){
        Timeslot timeslot = this.timeslotService.save(timeslotDtoMapper.fromTimeslotNewDtoToTimeslot(timeslotNew), timeslotNew.personId, timeslotNew.courtId);

        return new ResponseEntity<>(timeslotDtoMapper.fromTimeslotToTimeslotDto(timeslot), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<TimeslotDto> update(@PathVariable long id, @RequestBody TimeslotNewDto timeslotUpdate){
        Timeslot timeslotExisting = this.timeslotService.getById(id);
        if (timeslotExisting == null) {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }

        Timeslot timeslot = this.timeslotService.update(id, timeslotDtoMapper.fromTimeslotNewDtoToTimeslot(timeslotUpdate), timeslotUpdate.personId, timeslotUpdate.courtId);
        return new ResponseEntity<>(timeslotDtoMapper.fromTimeslotToTimeslotDto(timeslot), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable long id){
        Timeslot timeslotExisting = this.timeslotService.getById(id);
        if (timeslotExisting == null) {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }

        this.timeslotService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
