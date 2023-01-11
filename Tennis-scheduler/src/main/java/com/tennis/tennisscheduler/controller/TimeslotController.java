package com.tennis.tennisscheduler.controller;

import com.tennis.tennisscheduler.dto.TimeslotDto;
import com.tennis.tennisscheduler.dto.TimeslotResponseDto;
import com.tennis.tennisscheduler.mapper.TimeslotDtoMapper;
import com.tennis.tennisscheduler.mapper.TimeslotResponseDtoMapper;
import com.tennis.tennisscheduler.service.TimeslotService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<TimeslotDto> getAll(){

        return timeslotService.getAllTimeslots().stream()
                .map(timeslot ->timeslotDtoMapper.fromTimeslotToTimeslotDto(timeslot))
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyRole('ADMIN','TENNIS_PLAYER')")
    @GetMapping(value = "/{id}")
    public TimeslotDto getById(@PathVariable long id){

        return timeslotDtoMapper.fromTimeslotToTimeslotDto(timeslotService.getById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN','TENNIS_PLAYER')")
    @PostMapping(value = "/")
    public TimeslotResponseDto save(@RequestBody @Valid TimeslotDto timeslotNew, BindingResult result){

        return timeslotResponseDtoMapper.toTimeslotResponseDto(timeslotService.reserveTimeslot(timeslotDtoMapper.fromTimeslotDtoToTimeslot(timeslotNew), result));
    }

    @PreAuthorize("hasAnyRole('ADMIN','TENNIS_PLAYER')")
    @PutMapping(value = "/{id}")
    public TimeslotResponseDto update(@PathVariable long id, @RequestBody @Valid TimeslotDto timeslotUpdate, BindingResult result){

        return timeslotResponseDtoMapper
                .toTimeslotResponseDto(timeslotService.update(id, timeslotDtoMapper.fromTimeslotDtoToTimeslot(timeslotUpdate), result));
    }

    @PreAuthorize("hasAnyRole('ADMIN','TENNIS_PLAYER')")
    @PutMapping(value = "/cancel/{id}")
    public ResponseEntity<HttpStatus> cancelTimeslot(@PathVariable long id){
        timeslotService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
