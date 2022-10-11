package com.tennis.tennisscheduler.controllers;

import com.tennis.tennisscheduler.models.Person;
import com.tennis.tennisscheduler.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private PersonService personService;

    @GetMapping("/all")
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
        return new ResponseEntity<>(personService.getPersonsById(id),HttpStatus.OK);
    }
    @PutMapping("/updatePerson/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable long id,@RequestBody Person person){

        return new ResponseEntity<>(personService.updatePerson(id,person),HttpStatus.OK);
    }
}
