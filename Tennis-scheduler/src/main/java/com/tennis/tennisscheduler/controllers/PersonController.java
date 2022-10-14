package com.tennis.tennisscheduler.controllers;

import com.tennis.tennisscheduler.models.Person;
import com.tennis.tennisscheduler.services.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Person>>getAllPersons(){
        return new ResponseEntity<>(personService.getAllPersons(), HttpStatus.OK);
    }
    @PostMapping("/")
    public ResponseEntity<Person> savePerson(@RequestBody Person person) {
        return new ResponseEntity<>(personService.savePerson(person),HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePerson(@PathVariable long id) {
        personService.deletePersonById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable long id){
        return new ResponseEntity<>(personService.findById(id),HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable long id,@RequestBody Person person){
        return new ResponseEntity<>(personService.updatePerson(id,person),HttpStatus.OK);
    }
}
