package com.tennis.tennisscheduler.controllers;

import com.tennis.tennisscheduler.dtos.TennisCourtDto;
import com.tennis.tennisscheduler.exceptions.ApiRequestException;
import com.tennis.tennisscheduler.mappers.TennisCourtDtoMapper;
import com.tennis.tennisscheduler.models.TennisCourt;
import com.tennis.tennisscheduler.services.TennisCourtService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "tennis-courts")
@AllArgsConstructor
@SecurityRequirement(name = "Authorization")
public class TennisCourtController {
    public final TennisCourtService tennisCourtService;
    public final TennisCourtDtoMapper tennisCourtDtoMapper;
    @GetMapping("/")
    public ResponseEntity<List<TennisCourtDto>>getAllTennisCourts(){
        List<TennisCourtDto> tennisCourts = new ArrayList<>();
        for (TennisCourt tennisCourt : tennisCourtService.getAllTennisCourts())
            tennisCourts.add(tennisCourtDtoMapper.fromTennisCourtToTennisCourtDto(tennisCourt));

        return new ResponseEntity<>(tennisCourts, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/")
    public ResponseEntity<TennisCourtDto> saveTennisCourt(@RequestBody TennisCourtDto tennisCourtDto){
        TennisCourt tennisCourt = tennisCourtService.saveTennisCourt(tennisCourtDtoMapper.fromTennisCourtDtoToTennisCourt(tennisCourtDto));

        return new ResponseEntity<>(tennisCourtDtoMapper.fromTennisCourtToTennisCourtDto(tennisCourt),HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTennisCourt(@PathVariable long id){
//        TennisCourt tennisCourt =
        tennisCourtService.getTennisCourtById(id).orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND,"This id doesn't exist!"));
//        if (tennisCourt == null)
//            throw new ApiRequestException(HttpStatus.NOT_FOUND,"This id doesn't exist!");

        tennisCourtService.deleteTennisCourtById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TennisCourtDto>getTennisCourtById(@PathVariable long id){
        TennisCourt tennisCourt = tennisCourtService.getTennisCourtById(id).orElseThrow(()->new ApiRequestException(HttpStatus.NOT_FOUND,"This id doesn't exist!"));
//        if (tennisCourt == null)
//            throw new ApiRequestException(HttpStatus.NOT_FOUND,"This id doesn't exist!");

        return new ResponseEntity<>(tennisCourtDtoMapper.fromTennisCourtToTennisCourtDto(tennisCourt),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<TennisCourtDto> updateTennisCourt(@PathVariable long id, @RequestBody TennisCourtDto tennisCourtDto){
//        TennisCourt tennisCourtExisting =
        tennisCourtService.getTennisCourtById(id).orElseThrow(()->new ApiRequestException(HttpStatus.NOT_FOUND,"This id doesn't exist!"));
//        if (tennisCourtExisting == null)
//            throw new ApiRequestException(HttpStatus.NOT_FOUND,"This id doesn't exist!");

        TennisCourt tennisCourt = tennisCourtService.updateTennisCourt(id, tennisCourtDtoMapper.fromTennisCourtDtoToTennisCourt(tennisCourtDto));
        return new ResponseEntity<>(tennisCourtDtoMapper.fromTennisCourtToTennisCourtDto(tennisCourt),HttpStatus.OK);
    }

}