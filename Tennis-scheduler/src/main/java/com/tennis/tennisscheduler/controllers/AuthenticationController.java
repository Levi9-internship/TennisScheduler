package com.tennis.tennisscheduler.controllers;

import com.tennis.tennisscheduler.dtos.AuthenticationRequestDto;
import com.tennis.tennisscheduler.dtos.PersonDto;
import com.tennis.tennisscheduler.dtos.UserTokenStateDto;
import com.tennis.tennisscheduler.mappers.PersonDtoMapper;
import com.tennis.tennisscheduler.models.Person;
import com.tennis.tennisscheduler.services.PersonService;
import com.tennis.tennisscheduler.utils.TokenUtils;
import lombok.AllArgsConstructor;
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
    private final PersonDtoMapper personDtoMapper;

    @PostMapping("/login")
    public ResponseEntity<UserTokenStateDto> login(@RequestBody AuthenticationRequestDto authenticationRequest) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.email, authenticationRequest.password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Person user = (Person)authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user.getEmail());

        return ResponseEntity.ok(new UserTokenStateDto(jwt, user.getRole().getRoleName()));
    }

    @GetMapping("/logged-user")
    @PreAuthorize("hasAnyRole('TENNIS_PLAYER', 'ADMIN')")
    public ResponseEntity<PersonDto> getLoggedUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person person = (Person)authentication.getPrincipal();

        return ResponseEntity.ok(personDtoMapper.fromPersonToPersonDto(person));
    }
}
