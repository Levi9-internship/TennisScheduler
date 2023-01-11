package com.tennis.tennisscheduler.controller;

import com.tennis.tennisscheduler.dto.PersonDto;
import com.tennis.tennisscheduler.mapper.PersonDtoMapper;
import com.tennis.tennisscheduler.service.PersonService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/persons")
@AllArgsConstructor
@SecurityRequirement(name = "Authorization")
public class PersonController {

    private final PersonService personService;
    private final PersonDtoMapper personDtoMapper;

    @GetMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN','TENNIS_PLAYER')")
    public List<PersonDto>getAllPersons(){
        return personService.getAllPersons().stream()
                .map(timeslot ->personDtoMapper.fromPersonToPersonDto(timeslot))
                .collect(Collectors.toList());
    }

    @PostMapping("/")
    public PersonDto savePerson(@RequestBody PersonDto personNew) {
        return personDtoMapper.fromPersonToPersonDto(personService.savePerson(personDtoMapper.fromPersonDtoToPerson(personNew)));
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deletePerson(@PathVariable long id) {
        personService.deletePersonById(id);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('TENNIS_PLAYER', 'ADMIN')")
    public PersonDto getPersonById(@PathVariable long id){
        return personDtoMapper.fromPersonToPersonDto(personService.findById(id));
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('TENNIS_PLAYER','ADMIN')")
    public PersonDto updatePerson(@PathVariable long id,@RequestBody PersonDto personDto){
        return personDtoMapper.fromPersonToPersonDto(personService.updatePerson(id, personDtoMapper.fromPersonDtoToPerson(personDto)));
    }
}
