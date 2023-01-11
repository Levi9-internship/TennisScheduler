package com.tennis.tennisscheduler.service;

import com.tennis.tennisscheduler.model.Person;
import com.tennis.tennisscheduler.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    private final PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Person user = personRepository.findByEmail(email);
        if (user == null)
            throw new UsernameNotFoundException(String.format("No user found with email '%s'.", email));
        else
            return user;
    }

}
