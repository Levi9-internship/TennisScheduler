package com.tennis.tennisscheduler.controllers;

import com.tennis.tennisscheduler.dtos.PersonDto;
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

import java.util.ArrayList;
import java.util.List;

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

        List<PersonDto> persons = new ArrayList<>();
        for (Person person: personService.getAllPersons()) {
            persons.add(personDtoMapper.fromPersonToPersonDto(person));
        }
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
        Person personExisting = personService.findById(id);
        if (personExisting == null) {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }
        personService.deletePersonById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('TENNIS_PLAYER', 'ADMIN')")
    public ResponseEntity<PersonDto> getPersonById(@PathVariable long id){

        Person person = personService.findById(id);
        if(person==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(personDtoMapper.fromPersonToPersonDto(person),HttpStatus.OK);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('TENNIS_PLAYER','ADMIN')")
    public ResponseEntity<PersonDto> updatePerson(@PathVariable long id,@RequestBody PersonDto personDto){

        Person personExisting = personService.findById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person user = (Person) authentication.getPrincipal();

        if (personExisting == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        if(user.getRole().getRoleName().equals(UserType.ROLE_TENNIS_PLAYER) && id != user.getId())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        
        Person person = personService.updatePerson(id, personDtoMapper.fromPersonDtoToPerson(personDto));
        return new ResponseEntity<>(personDtoMapper.fromPersonToPersonDto(person), HttpStatus.OK);
    }
}
