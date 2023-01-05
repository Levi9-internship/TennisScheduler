package com.tennis.tennisscheduler.controllers;

import com.tennis.tennisscheduler.dtos.PersonDto;
import com.tennis.tennisscheduler.exceptions.ApiRequestException;
import com.tennis.tennisscheduler.mappers.PersonDtoMapper;
import com.tennis.tennisscheduler.models.Person;
import com.tennis.tennisscheduler.models.enumes.UserType;
import com.tennis.tennisscheduler.services.PersonService;
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
    public ResponseEntity<List<PersonDto>>getAllPersons(){
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

        return new ResponseEntity<>(persons, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<PersonDto> savePerson(@RequestBody PersonDto personNew) {
        Person person = personService.savePerson(personDtoMapper.fromPersonDtoToPerson(personNew));
        return new ResponseEntity<>(personDtoMapper.fromPersonToPersonDto(person),HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deletePerson(@PathVariable long id) {
        personService.findById(id).orElseThrow(()->new ApiRequestException(HttpStatus.NOT_FOUND,"This id doesn't exist!"));
        personService.deletePersonById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('TENNIS_PLAYER', 'ADMIN')")
    public ResponseEntity<PersonDto> getPersonById(@PathVariable long id){
        return new ResponseEntity<>(personDtoMapper.fromPersonToPersonDto(personService
                .findById(id)
                .orElseThrow(()->new ApiRequestException(HttpStatus.NOT_FOUND,"This id doesn't exist!")))
                ,HttpStatus.OK);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('TENNIS_PLAYER','ADMIN')")
    public ResponseEntity<PersonDto> updatePerson(@PathVariable long id,@RequestBody PersonDto personDto){
        personService.findById(id).orElseThrow(()->new ApiRequestException(HttpStatus.NOT_FOUND,"This id doesn't exist!"));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person user = (Person) authentication.getPrincipal();

        if(user.getRole().getRoleName().equals(UserType.ROLE_TENNIS_PLAYER) && id != user.getId())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        
        Person person = personService.updatePerson(id, personDtoMapper.fromPersonDtoToPerson(personDto));
        return new ResponseEntity<>(personDtoMapper.fromPersonToPersonDto(person), HttpStatus.OK);
    }
}
