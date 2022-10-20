package com.tennis.tennisscheduler.controllers;

import com.tennis.tennisscheduler.dtos.JwtAuthenticationRequest;
import com.tennis.tennisscheduler.dtos.UserTokenState;
import com.tennis.tennisscheduler.models.Person;
import com.tennis.tennisscheduler.utils.TokenUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final TokenUtils tokenUtils;

    @PostMapping("/login")
    public ResponseEntity<UserTokenState> createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest) throws Exception {

        Authentication authentication = null;

        authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.email, authenticationRequest.password));


        SecurityContextHolder.getContext().setAuthentication(authentication);

        Person user = (Person) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user.getEmail());
        int expiresIn = tokenUtils.getExpiredIn();
        if (!user.isEnabled()) {
            return ResponseEntity.ok(new UserTokenState(jwt,user.getRole().getRoleName().toString(), user.isEnabled(), user.getId()));
        }

        return ResponseEntity.ok(new UserTokenState(jwt, user.getRole().getRoleName().toString(), user.isEnabled(), user.getId()));
    }
}
