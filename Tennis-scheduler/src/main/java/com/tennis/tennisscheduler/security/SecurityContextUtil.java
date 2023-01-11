package com.tennis.tennisscheduler.security;

import com.tennis.tennisscheduler.model.Person;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityContextUtil {
    static public Person GetLoggedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (Person)authentication.getPrincipal();
    }
}
