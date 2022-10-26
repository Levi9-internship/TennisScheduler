package com.tennis.tennisscheduler.controllers;

import com.tennis.tennisscheduler.dtos.AuthenticationRequestDto;
import com.tennis.tennisscheduler.dtos.UserTokenStateDto;
import com.tennis.tennisscheduler.models.Person;
import com.tennis.tennisscheduler.models.Timeslot;
import com.tennis.tennisscheduler.services.TimeslotService;
import com.tennis.tennisscheduler.utils.TokenUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authentication")
@AllArgsConstructor
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final TokenUtils tokenUtils;
    private final TimeslotService timeslotService;

    @PostMapping("/login")
    public ResponseEntity<UserTokenStateDto> login(@RequestBody AuthenticationRequestDto authenticationRequest) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.email, authenticationRequest.password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Person user = (Person)authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user.getEmail());

        return ResponseEntity.ok(new UserTokenStateDto(jwt, user.getRole().getRoleName()));
    }

}
