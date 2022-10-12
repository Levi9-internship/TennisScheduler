package com.tennis.tennisscheduler.controllers;


import com.tennis.tennisscheduler.models.TennisCourt;
import com.tennis.tennisscheduler.services.TennisCourtServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tenniscourt")
public class TennisCourtController {
    @Autowired
    private TennisCourtServices tennisCourtServices;

    @GetMapping("/all")
    public ResponseEntity<List<TennisCourt>>getAllTennisCourts(){
        return new ResponseEntity<>(tennisCourtServices.getAllTennisCourts(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<TennisCourt> SaveTennisCourt(@RequestBody TennisCourt tennisCourt){
        return new ResponseEntity<>(tennisCourtServices.saveTennisCourts(tennisCourt),HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTennisCourt(@PathVariable long id){
        tennisCourtServices.deleteTennisCourtById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TennisCourt>getTennisCourtById(@PathVariable long id){
        return new ResponseEntity<>(tennisCourtServices.getTennisCourtById(id),HttpStatus.OK);
    }

    @PutMapping("/updateTennisCourt/{id}")
    public ResponseEntity<TennisCourt> updateTennisCourt(@PathVariable long id, @RequestBody TennisCourt tennisCourt){
        return new ResponseEntity<>(tennisCourtServices.updateTennisCourt(id,tennisCourt),HttpStatus.OK);
    }

}
