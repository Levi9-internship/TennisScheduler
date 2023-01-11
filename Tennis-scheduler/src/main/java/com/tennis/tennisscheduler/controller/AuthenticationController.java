package com.tennis.tennisscheduler.controller;

import com.tennis.tennisscheduler.dto.AuthenticationRequestDto;
import com.tennis.tennisscheduler.dto.UpdatePasswordDto;
import com.tennis.tennisscheduler.dto.UserTokenStateDto;
import com.tennis.tennisscheduler.dto.UserWithChangedPasswordDto;
import com.tennis.tennisscheduler.dto.PersonDto;
import com.tennis.tennisscheduler.mapper.PersonDtoMapper;

import com.tennis.tennisscheduler.security.SecurityContextUtil;
import com.tennis.tennisscheduler.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authentication")
@AllArgsConstructor
public class AuthenticationController {
    private final PersonService personService;
    private final PersonDtoMapper personDtoMapper;

    @PostMapping("/login")
    public UserTokenStateDto login(@RequestBody AuthenticationRequestDto authenticationRequest) {
        return new UserTokenStateDto(personService.login(authenticationRequest.getEmail(),authenticationRequest.getPassword()));
    }


    @PreAuthorize("hasAnyRole('TENNIS_PLAYER','ADMIN')")
    @PutMapping("/change-password")
    public UserWithChangedPasswordDto updatePersonPassword(@RequestBody UpdatePasswordDto updatePasswordDto) {
        return personService.updatePersonPassword(updatePasswordDto.getOldPassword(),updatePasswordDto.getNewPassword());
    }
    @GetMapping("/logged-user")
    @PreAuthorize("hasAnyRole('TENNIS_PLAYER', 'ADMIN')")
    public PersonDto getLoggedUser() {
        return personDtoMapper.fromPersonToPersonDto(SecurityContextUtil.GetLoggedUser());
    }

}
