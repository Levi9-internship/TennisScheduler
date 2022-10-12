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
        List<TimeslotDto> ret = new ArrayList<>();
        for (Timeslot timeslot: timeslotService.getAll()) {
            ret.add(timeslotDtoMapper.fromTimeslotToTimeslotDto(timeslot));
        }
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TimeslotDto> getById(@PathVariable long id){
        Timeslot timeslot = this.timeslotService.getById(id);
        return new ResponseEntity<>(timeslotDtoMapper.fromTimeslotToTimeslotDto(timeslot), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TimeslotDto> save(@RequestBody TimeslotNewDto timeslotNew){
        Timeslot timeslot = this.timeslotService.save(timeslotDtoMapper.fromTimeslotNewDtoToTimeslot(timeslotNew), timeslotNew.personId, timeslotNew.courtId);
        return new ResponseEntity<>(timeslotDtoMapper.fromTimeslotToTimeslotDto(timeslot), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<TimeslotDto> update(@PathVariable long id, @RequestBody TimeslotNewDto timeslotUpdate){
        Timeslot timeslot = this.timeslotService.update(id, timeslotDtoMapper.fromTimeslotNewDtoToTimeslot(timeslotUpdate), timeslotUpdate.personId, timeslotUpdate.courtId);
        return new ResponseEntity<>(timeslotDtoMapper.fromTimeslotToTimeslotDto(timeslot), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable long id){
        this.timeslotService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
