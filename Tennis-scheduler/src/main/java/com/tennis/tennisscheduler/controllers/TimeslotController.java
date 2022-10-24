package com.tennis.tennisscheduler.controllers;

import com.tennis.tennisscheduler.dtos.TimeslotDto;
import com.tennis.tennisscheduler.dtos.TimeslotResponseDto;
import com.tennis.tennisscheduler.mappers.TimeslotDtoMapper;
import com.tennis.tennisscheduler.mappers.TimeslotResponseDtoMapper;
import com.tennis.tennisscheduler.models.Timeslot;
import com.tennis.tennisscheduler.services.TimeslotService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "timeslots")
@AllArgsConstructor
public class TimeslotController {

    private final TimeslotService timeslotService;
    private final TimeslotDtoMapper timeslotDtoMapper;
    private final TimeslotResponseDtoMapper timeslotResponseDtoMapper;

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

    @PostMapping("/")
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

    @PutMapping(value = "/{id}")
    public ResponseEntity<TimeslotResponseDto> update(@PathVariable long id, @RequestBody TimeslotDto timeslotUpdate){
        Timeslot timeslotExisting = timeslotService.getById(id);
        if (timeslotExisting == null) {
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        }

        TimeslotResponseDto timeslotResponse = timeslotResponseDtoMapper.toTimeslotResponseDto(timeslotService.update(id, timeslotDtoMapper.fromTimeslotDtoToTimeslot(timeslotUpdate)));
        if(timeslotResponse.timeslot != null)
            return new ResponseEntity<>(timeslotResponse, HttpStatus.OK);
        else
            return new ResponseEntity<>(timeslotResponse, HttpStatus.BAD_REQUEST);
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
