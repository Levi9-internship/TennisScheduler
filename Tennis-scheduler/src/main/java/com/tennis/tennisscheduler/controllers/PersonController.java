package com.tennis.tennisscheduler.controllers;

import com.tennis.tennisscheduler.services.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person")
public class PersonController {

    private PersonService personService;


    @PostMapping
    public String display()
    {
        return "index";
    }
}
