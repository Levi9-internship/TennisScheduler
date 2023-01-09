package com.tennis.tennisscheduler.controller;

import com.tennis.tennisscheduler.dto.AuthenticationRequestDto;
import com.tennis.tennisscheduler.dto.UpdatePasswordDto;
import com.tennis.tennisscheduler.dto.UserTokenStateDto;
import com.tennis.tennisscheduler.dto.UserWithChangedPasswordDto;
import com.tennis.tennisscheduler.dto.PersonDto;
import com.tennis.tennisscheduler.mapper.PersonDtoMapper;
import com.tennis.tennisscheduler.model.Person;
import com.tennis.tennisscheduler.message.UpdatePersonPasswordMessages;

import com.tennis.tennisscheduler.service.PersonService;
import com.tennis.tennisscheduler.util.TokenUtils;
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
    private final PersonService personService;
    private final PersonDtoMapper personDtoMapper;

    @PostMapping("/login")
    public ResponseEntity<UserTokenStateDto> login(@RequestBody AuthenticationRequestDto authenticationRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getEmail(), authenticationRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        Person user = (Person)authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user.getEmail(), user.getRole().getRoleName());

        return ResponseEntity.ok(new UserTokenStateDto(jwt));
    }


    @PreAuthorize("hasAnyRole('TENNIS_PLAYER','ADMIN')")
    @PutMapping("/change-password")
    public ResponseEntity<UserWithChangedPasswordDto> updatePersonPassword(@RequestBody UpdatePasswordDto updatePasswordDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person user = (Person) authentication.getPrincipal();
        String message;
        String jwt = "";

        if (personService.updatePassword(user.getId(), updatePasswordDto.getOldPassword(), updatePasswordDto.getNewPassword())) {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), updatePasswordDto.getNewPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            jwt = tokenUtils.generateToken(user.getEmail(), user.getRole().getRoleName());
            message = UpdatePersonPasswordMessages.SUCCESSFULLY_CHANGED_PASSWORD;
        } else {
            message = UpdatePersonPasswordMessages.UNSUCCESSFULLY_CHANGED_PASSWORD;
            return new ResponseEntity<>(new UserWithChangedPasswordDto(new UserTokenStateDto(jwt), message)
                    ,HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity<>(new UserWithChangedPasswordDto(new UserTokenStateDto(jwt), message)
                , HttpStatus.OK);
    }
    @GetMapping("/logged-user")
    @PreAuthorize("hasAnyRole('TENNIS_PLAYER', 'ADMIN')")
    public ResponseEntity<PersonDto> getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person person = (Person)authentication.getPrincipal();

        return ResponseEntity.ok(personDtoMapper.fromPersonToPersonDto(person));
    }

}
