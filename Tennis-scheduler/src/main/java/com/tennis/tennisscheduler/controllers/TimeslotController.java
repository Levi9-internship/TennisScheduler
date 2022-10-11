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

    @PostMapping
    public ResponseEntity<TimeslotDto> save(@RequestBody Timeslot timeslotNew){
        Timeslot timeslot = this.timeslotService.save(timeslotNew);
        return new ResponseEntity<>(new TimeslotDto(timeslot.getId(), timeslot.getStartDate(), timeslot.getEndDate(), timeslot.getDuration()), HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<TimeslotNewDto> saveDto(@RequestBody TimeslotNewDto timeslotNew){
        Timeslot timeslot = this.timeslotService.saveDto(timeslotNew);
        return new ResponseEntity<>(new TimeslotNewDto(timeslot.getId(), timeslot.getStartDate(), timeslot.getEndDate(), timeslot.getDuration(), timeslot.getPerson().getId(), timeslot.getTennisCourt().getId()), HttpStatus.CREATED);
    }

}
