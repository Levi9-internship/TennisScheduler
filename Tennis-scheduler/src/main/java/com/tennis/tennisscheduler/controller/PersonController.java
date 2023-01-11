package com.tennis.tennisscheduler.controller;

import com.tennis.tennisscheduler.dto.PersonDto;
import com.tennis.tennisscheduler.exception.ApiRequestException;
import com.tennis.tennisscheduler.mapper.PersonDtoMapper;
import com.tennis.tennisscheduler.model.Person;
import com.tennis.tennisscheduler.model.enumes.UserType;
import com.tennis.tennisscheduler.service.PersonService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person user = (Person)authentication.getPrincipal();
        List<PersonDto> persons;

        if(user.getRole().getRoleName().equals(UserType.ROLE_TENNIS_PLAYER))
             persons = personService.getAllPersons().stream()
                    .filter(person -> !((person.getRole().getRoleName().equals(UserType.ROLE_ADMIN))))
                    .map(person -> personDtoMapper.fromPersonToPersonDto(person))
                    .collect(Collectors.toList());
        else
             persons = personService.getAllPersons().stream().map(person -> personDtoMapper.fromPersonToPersonDto(person)).collect(Collectors.toList());

        return persons;
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
