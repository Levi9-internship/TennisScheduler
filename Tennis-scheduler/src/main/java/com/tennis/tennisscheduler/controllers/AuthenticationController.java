package com.tennis.tennisscheduler.controllers;

import com.tennis.tennisscheduler.dtos.AuthenticationRequestDto;
import com.tennis.tennisscheduler.dtos.UpdatePasswordDto;
import com.tennis.tennisscheduler.dtos.UserTokenStateDto;
import com.tennis.tennisscheduler.dtos.UserWithChangedPasswordDto;
import com.tennis.tennisscheduler.dtos.PersonDto;
import com.tennis.tennisscheduler.dtos.UserTokenStateDto;
import com.tennis.tennisscheduler.mappers.PersonDtoMapper;
import com.tennis.tennisscheduler.models.Person;
import com.tennis.tennisscheduler.services.PersonService;
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
    private final PersonService personService;
    private final PersonDtoMapper personDtoMapper;

    @PostMapping("/login")
    public ResponseEntity<UserTokenStateDto> login(@RequestBody AuthenticationRequestDto authenticationRequest) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.email, authenticationRequest.password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Person user = (Person)authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user.getEmail(), user.getRole().getRoleName());

        return ResponseEntity.ok(new UserTokenStateDto(jwt, user.getRole().getRoleName()));
    }


    @PreAuthorize("hasAnyRole('TENNIS_PLAYER','ADMIN')")
    @PutMapping("/change-password")
    public ResponseEntity<UserWithChangedPasswordDto> updatePersonPassword(@RequestBody UpdatePasswordDto updatePasswordDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person user = (Person) authentication.getPrincipal();
        String message;
        String jwt = "";

        if (personService.updatePassword(user.getId(), updatePasswordDto.oldPassword, updatePasswordDto.newPassword)) {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    user.getEmail(), updatePasswordDto.newPassword));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            jwt = tokenUtils.generateToken(user.getEmail());
            message = "Successfully changed password.";
        } else {
            message = "Current password is not correct.";
            return new ResponseEntity<>(new UserWithChangedPasswordDto(new UserTokenStateDto(jwt, user.getRole().getRoleName()), message)
                    , HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity<>(new UserWithChangedPasswordDto(new UserTokenStateDto(jwt, user.getRole().getRoleName()), message)
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
