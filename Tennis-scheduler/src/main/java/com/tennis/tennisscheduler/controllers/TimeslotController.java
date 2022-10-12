package com.tennis.tennisscheduler.controllers;

import com.tennis.tennisscheduler.dtos.TimeslotDto;
import com.tennis.tennisscheduler.dtos.TimeslotNewDto;
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

    @GetMapping
    public ResponseEntity<List<TimeslotDto>> getAll(){
        List<TimeslotDto> ret = new ArrayList<>();
        for (Timeslot timeslot: timeslotService.getAll()) {
            ret.add(new TimeslotDto(timeslot.getId(), timeslot.getStartDate(), timeslot.getEndDate(), timeslot.getDuration()));
        }
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TimeslotDto> getById(@PathVariable long id){
        Timeslot timeslot = this.timeslotService.getById(id);
        return new ResponseEntity<>(new TimeslotDto(timeslot.getId(), timeslot.getStartDate(), timeslot.getEndDate(), timeslot.getDuration()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TimeslotDto> save(@RequestBody TimeslotNewDto timeslotNew){
        Timeslot timeslot = this.timeslotService.save(timeslotNew);
        return new ResponseEntity<>(new TimeslotDto(timeslot.getId(), timeslot.getStartDate(), timeslot.getEndDate(), timeslot.getDuration()), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<TimeslotDto> update(@PathVariable long id, @RequestBody TimeslotNewDto timeslotUpdate){
        Timeslot timeslot = this.timeslotService.update(id,timeslotUpdate);
        return new ResponseEntity<>(new TimeslotDto(timeslot.getId(), timeslot.getStartDate(), timeslot.getEndDate(), timeslot.getDuration()), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable long id){
        this.timeslotService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
