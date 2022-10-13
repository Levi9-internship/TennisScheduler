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
@RequestMapping(value = "tenniscourt")
public class TennisCourtController {

    private TennisCourtServices tennisCourtServices;
    private TennisCourtDtoMapper tennisCourtDtoMapper;
    public TennisCourtController(TennisCourtServices tennisCourtServices,TennisCourtDtoMapper tennisCourtDtoMapper){
        this.tennisCourtServices=tennisCourtServices;
        this.tennisCourtDtoMapper=tennisCourtDtoMapper;
    }

    @GetMapping("/tennis-courts")
    public ResponseEntity<List<TennisCourtDto>>getAllTennisCourts(){
        List<TennisCourtDto> tennisCourts = new ArrayList<>();
        for (TennisCourt tennisCourt : tennisCourtServices.getAllTennisCourts()){
            tennisCourts.add(tennisCourtDtoMapper.fromTennisCourtToTennisCourtDto(tennisCourt));
        }
        return new ResponseEntity<>(tennisCourts, HttpStatus.OK);
    }

    @PostMapping("/tennis-court")
    public ResponseEntity<TennisCourtDto> SaveTennisCourt(@RequestBody TennisCourtDto tennisCourtDto){
        TennisCourt tennisCourt = tennisCourtServices.saveTennisCourts(tennisCourtDtoMapper.fromTennisCourtDtoToTennisCourt(tennisCourtDto));

        return new ResponseEntity<>(tennisCourtDtoMapper.fromTennisCourtToTennisCourtDto(tennisCourt),HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTennisCourt(@PathVariable long id){
        TennisCourt tennisCourt = tennisCourtServices.getTennisCourtById(id);
        if (tennisCourt == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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

    @PutMapping("/tennis-court/{id}")
    public ResponseEntity<TennisCourtDto> updateTennisCourt(@PathVariable long id, @RequestBody TennisCourtDto tennisCourtDto){
        TennisCourt tennisCourtExisting = tennisCourtServices.getTennisCourtById(id);
        if (tennisCourtExisting == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        TennisCourt tennisCourt = tennisCourtServices.updateTennisCourt(tennisCourtDtoMapper.fromTennisCourtDtoToTennisCourt(tennisCourtDto));
        return new ResponseEntity<>(tennisCourtDtoMapper.fromTennisCourtToTennisCourtDto(tennisCourt),HttpStatus.OK);
    }

}
