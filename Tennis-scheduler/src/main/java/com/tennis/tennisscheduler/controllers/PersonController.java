package com.tennis.tennisscheduler.controllers;

import com.tennis.tennisscheduler.services.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PersonController {

    private PersonService personService;

    @RequestMapping("/")
    public String display()
    {
        return "index";
    }
}
