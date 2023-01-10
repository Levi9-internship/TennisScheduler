package com.tennis.tennisscheduler.controller;

import com.tennis.tennisscheduler.dto.TimeslotDto;
import com.tennis.tennisscheduler.dto.TimeslotResponseDto;
import com.tennis.tennisscheduler.exception.ApiRequestException;
import com.tennis.tennisscheduler.mapper.TimeslotDtoMapper;
import com.tennis.tennisscheduler.mapper.TimeslotResponseDtoMapper;
import com.tennis.tennisscheduler.model.Person;
import com.tennis.tennisscheduler.model.Timeslot;
import com.tennis.tennisscheduler.model.enumes.UserType;
import com.tennis.tennisscheduler.service.TimeslotService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponseEntity<List<TimeslotDto>> getAll(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person user = (Person)authentication.getPrincipal();

        List<TimeslotDto> timeslots;
        if(user.getRole().getRoleName().equals(UserType.ROLE_TENNIS_PLAYER))
            timeslots = timeslotService.getAllTimeslotsForUser(user.getId()).stream()
                    .map(timeslot ->timeslotDtoMapper.fromTimeslotToTimeslotDto(timeslot))
                    .collect(Collectors.toList());
        else
            timeslots = timeslotService.getAll().stream()
                    .map(timeslot -> timeslotDtoMapper.fromTimeslotToTimeslotDto(timeslot))
                    .collect(Collectors.toList());

        return new ResponseEntity<>(timeslots, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','TENNIS_PLAYER')")
    @GetMapping(value = "/{id}")
    public TimeslotDto getById(@PathVariable long id){
        return timeslotDtoMapper.fromTimeslotToTimeslotDto(timeslotService.getById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN','TENNIS_PLAYER')")
    @PostMapping(value = "/")
    public ResponseEntity<TimeslotResponseDto> save(@RequestBody @Valid TimeslotDto timeslotNew, BindingResult result){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person user = (Person)authentication.getPrincipal();

        if (!result.hasErrors() && user.getRole().getRoleName().equals(UserType.ROLE_TENNIS_PLAYER)){
            timeslotNew.setPersonId(user.getId());
            return new ResponseEntity<>(timeslotResponseDtoMapper.toTimeslotResponseDto(timeslotService.reserveTimeslot(timeslotDtoMapper.fromTimeslotDtoToTimeslot(timeslotNew))), HttpStatus.CREATED);
        }
        else if(!result.hasErrors() && user.getRole().getRoleName().equals(UserType.ROLE_ADMIN))
            return new ResponseEntity<>(timeslotResponseDtoMapper.toTimeslotResponseDto(timeslotService.reserveTimeslot(timeslotDtoMapper.fromTimeslotDtoToTimeslot(timeslotNew))), HttpStatus.CREATED);

        return new ResponseEntity<>(new TimeslotResponseDto(result.getAllErrors()), HttpStatus.BAD_REQUEST);
    }

//    @PreAuthorize("hasAnyRole('ADMIN','TENNIS_PLAYER')")
//    @PutMapping(value = "/{id}")
//    public ResponseEntity<TimeslotResponseDto> update(@PathVariable long id, @RequestBody @Valid TimeslotDto timeslotUpdate, BindingResult result){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Person user = (Person)authentication.getPrincipal();
//        Timeslot timeslotExisting = timeslotService.getById(id).orElseThrow(()->new ApiRequestException(HttpStatus.NOT_FOUND,"This id doesn't exist!"));
//
//        if(result.hasErrors())
//            return new ResponseEntity<>(new TimeslotResponseDto(result.getAllErrors()), HttpStatus.BAD_REQUEST);
//
//        TimeslotResponseDto timeslotResponseDto = timeslotResponseDtoMapper
//                .toTimeslotResponseDto(timeslotService.update(id, timeslotDtoMapper.fromTimeslotDtoToTimeslot(timeslotUpdate)));
//
//        if(timeslotResponseDto.getTimeslot() == null)
//            throw new ApiRequestException(HttpStatus.BAD_REQUEST,"There is no timeslots");
//
//        if (timeslotExisting == null)
//            throw new ApiRequestException(HttpStatus.NOT_FOUND,"This id doesn't exist!");
//
//        if(user.getRole().getRoleName().equals(UserType.ROLE_TENNIS_PLAYER) && timeslotExisting.getPerson().getId() != user.getId())
//            throw new ApiRequestException(HttpStatus.UNAUTHORIZED,"You don't have permission");
//
//        timeslotUpdate.setPersonId(user.getId());
//        return new ResponseEntity<>(timeslotResponseDto, HttpStatus.OK);
//    }
//
//    @PreAuthorize("hasAnyRole('ADMIN','TENNIS_PLAYER')")
//    @PutMapping(value = "/cancel/{id}")
//    public ResponseEntity<HttpStatus> cancelTimeslot(@PathVariable long id){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Person user = (Person)authentication.getPrincipal();
//        Timeslot timeslotExisting = timeslotService.getById(id).orElseThrow(()->new ApiRequestException(HttpStatus.NOT_FOUND,"This id doesn't exist!"));
//
//        if (user.getRole().getRoleName().equals(UserType.ROLE_TENNIS_PLAYER) && timeslotExisting.getPerson().getId() != user.getId())
//            throw new ApiRequestException(HttpStatus.UNAUTHORIZED,"You don't have permission");
//
//        timeslotService.deleteById(id);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
