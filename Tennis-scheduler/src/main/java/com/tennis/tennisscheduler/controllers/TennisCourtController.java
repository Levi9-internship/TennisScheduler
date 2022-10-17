package com.tennis.tennisscheduler.controllers;

import com.tennis.tennisscheduler.dtos.TennisCourtDto;
import com.tennis.tennisscheduler.mappers.TennisCourtDtoMapper;
import com.tennis.tennisscheduler.models.TennisCourt;
import com.tennis.tennisscheduler.services.TennisCourtServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "tennis-courts")
public class TennisCourtController {


    public final  TennisCourtServices tennisCourtServices;
    public final TennisCourtDtoMapper tennisCourtDtoMapper;

    public TennisCourtController(TennisCourtServices tennisCourtServices, TennisCourtDtoMapper tennisCourtDtoMapper){
        this.tennisCourtServices=tennisCourtServices;
        this.tennisCourtDtoMapper=tennisCourtDtoMapper;
    }

    @GetMapping("/")
    public ResponseEntity<List<TennisCourtDto>>getAllTennisCourts(){
        List<TennisCourtDto> tennisCourts = new ArrayList<>();
        for (TennisCourt tennisCourt : tennisCourtServices.getAllTennisCourts()){
            tennisCourts.add(tennisCourtDtoMapper.fromTennisCourtToTennisCourtDto(tennisCourt));
        }
        return new ResponseEntity<>(tennisCourts, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<TennisCourtDto> saveTennisCourt(@RequestBody TennisCourtDto tennisCourtDto){
        TennisCourt tennisCourt = tennisCourtServices.saveTennisCourt(tennisCourtDtoMapper.fromTennisCourtDtoToTennisCourt(tennisCourtDto));

        return new ResponseEntity<>(tennisCourtDtoMapper.fromTennisCourtToTennisCourtDto(tennisCourt),HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTennisCourt(@PathVariable long id){
        TennisCourt tennisCourt = tennisCourtServices.getTennisCourtById(id);
        if (tennisCourt == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        tennisCourtServices.deleteTennisCourtById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TennisCourtDto>getTennisCourtById(@PathVariable long id){
        TennisCourt tennisCourt = tennisCourtServices.getTennisCourtById(id);
        if (tennisCourt == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(tennisCourtDtoMapper.fromTennisCourtToTennisCourtDto(tennisCourt),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TennisCourtDto> updateTennisCourt(@PathVariable long id, @RequestBody TennisCourtDto tennisCourtDto){
        TennisCourt tennisCourtExisting = tennisCourtServices.getTennisCourtById(id);
        if (tennisCourtExisting == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        TennisCourt tennisCourt = tennisCourtServices.updateTennisCourt(id, tennisCourtDtoMapper.fromTennisCourtDtoToTennisCourt(tennisCourtDto));
        return new ResponseEntity<>(tennisCourtDtoMapper.fromTennisCourtToTennisCourtDto(tennisCourt),HttpStatus.OK);
    }

}
