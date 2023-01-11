package com.tennis.tennisscheduler.controller;

import com.tennis.tennisscheduler.dto.TennisCourtDto;
import com.tennis.tennisscheduler.exception.ApiRequestException;
import com.tennis.tennisscheduler.mapper.TennisCourtDtoMapper;
import com.tennis.tennisscheduler.model.TennisCourt;
import com.tennis.tennisscheduler.service.TennisCourtService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "tennis-courts")
@AllArgsConstructor
@SecurityRequirement(name = "Authorization")
public class TennisCourtController {
    public final TennisCourtService tennisCourtService;
    public final TennisCourtDtoMapper tennisCourtDtoMapper;
    @GetMapping("/")
    public List<TennisCourtDto> getAllTennisCourts(){
        return tennisCourtService.getAllTennisCourts().stream().map(tennisCourt -> tennisCourtDtoMapper.fromTennisCourtToTennisCourtDto(tennisCourt)).collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/")
    public TennisCourtDto saveTennisCourt(@RequestBody TennisCourtDto tennisCourtDto){
        return tennisCourtDtoMapper.fromTennisCourtToTennisCourtDto(tennisCourtService.saveTennisCourt(tennisCourtDtoMapper.fromTennisCourtDtoToTennisCourt(tennisCourtDto)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteTennisCourt(@PathVariable long id){
        tennisCourtService.deleteTennisCourtById(id);
    }

    @GetMapping("/{id}")
    public TennisCourtDto getTennisCourtById(@PathVariable long id){
        return tennisCourtDtoMapper.fromTennisCourtToTennisCourtDto(tennisCourtService.getTennisCourtById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public TennisCourtDto updateTennisCourt(@PathVariable long id, @RequestBody TennisCourtDto tennisCourtDto){
        return tennisCourtDtoMapper.fromTennisCourtToTennisCourtDto(tennisCourtService.updateTennisCourt(id, tennisCourtDtoMapper.fromTennisCourtDtoToTennisCourt(tennisCourtDto)));
    }

}