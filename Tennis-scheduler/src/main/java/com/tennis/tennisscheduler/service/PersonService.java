package com.tennis.tennisscheduler.service;

import com.tennis.tennisscheduler.dto.UserTokenStateDto;
import com.tennis.tennisscheduler.dto.UserWithChangedPasswordDto;
import com.tennis.tennisscheduler.exception.ApiRequestException;
import com.tennis.tennisscheduler.message.UpdatePersonPasswordMessages;
import com.tennis.tennisscheduler.model.Person;
import com.tennis.tennisscheduler.model.enumes.UserType;

import com.tennis.tennisscheduler.repository.PersonRepository;
import com.tennis.tennisscheduler.repository.RoleRepository;
import com.tennis.tennisscheduler.security.SecurityContextUtil;
import com.tennis.tennisscheduler.util.TokenUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PersonService{

    private final PersonRepository personRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenUtils tokenUtils;

    public List<Person> getAllPersons() {

        if(SecurityContextUtil.GetLoggedUser().getRole().getRoleName().equals(UserType.ROLE_TENNIS_PLAYER))
            return personRepository.findAll().stream()
                    .filter(person -> !((person.getRole().getRoleName().equals(UserType.ROLE_ADMIN))))
                    .collect(Collectors.toList());
        else
            return new ArrayList<>(personRepository.findAll());
    }

    public Person findById(long id) {
        return personRepository.findById(id).orElseThrow(()->new ApiRequestException(HttpStatus.NOT_FOUND,"This id isn't valid"));
    }

    public Person savePerson(Person person) {
        person.setRole(roleRepository.findByRoleName(UserType.ROLE_TENNIS_PLAYER));
        person.setEnabled(true);
        person.setPassword(bCryptPasswordEncoder.encode(person.getPassword()));
        return personRepository.save(person);
    }

    public void deletePersonById(long id) {
        personRepository.findById(id).orElseThrow(()->new ApiRequestException(HttpStatus.NOT_FOUND,"This id isn't valid"));
        personRepository.deleteById(id);
    }

    public Person updatePerson(long id, Person person) throws ApiRequestException{

        if(SecurityContextUtil.GetLoggedUser().getRole().getRoleName().equals(UserType.ROLE_TENNIS_PLAYER) && id != SecurityContextUtil.GetLoggedUser().getId())
            throw new ApiRequestException(HttpStatus.UNAUTHORIZED,"Tennis player can't change person data");

        Person existingPerson = personRepository.findById(id).orElseThrow(()->new ApiRequestException(HttpStatus.NOT_FOUND,"This id isn't valid"));;
        existingPerson.setFirstName(person.getFirstName());
        existingPerson.setLastName(person.getLastName());
        existingPerson.setBirthday(person.getBirthday());
        existingPerson.setEmail(person.getEmail());
        existingPerson.setPhoneNumber(person.getPhoneNumber());
        existingPerson.setGender(person.getGender());
        existingPerson.setAddress(person.getAddress());
        return personRepository.save(existingPerson);
    }

    public Boolean updatePassword(long id, String oldPassword, String newPassword) {
        Person existingPerson = personRepository.findById(id).orElseThrow(()->new ApiRequestException(HttpStatus.NOT_FOUND,"This id is not valid!"));

        if (bCryptPasswordEncoder.matches(oldPassword, existingPerson.getPassword())) {
            existingPerson.setPassword(bCryptPasswordEncoder.encode(newPassword));
            personRepository.save(existingPerson);
        }
        else
            return false;

        return true;
    }

    public String login(String email, String password){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        Person user = (Person)authentication.getPrincipal();
        return tokenUtils.generateToken(user.getEmail(), user.getRole().getRoleName());
    }

    public UserWithChangedPasswordDto updatePersonPassword(String oldPassword, String newPassword){
        Person user = SecurityContextUtil.GetLoggedUser();
        String message;
        String jwt = "";

        if (updatePassword(user.getId(), oldPassword,newPassword)) {
            SecurityContextHolder.getContext().setAuthentication(authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), newPassword)));
            jwt = tokenUtils.generateToken(user.getEmail(), user.getRole().getRoleName());
            message = UpdatePersonPasswordMessages.SUCCESSFULLY_CHANGED_PASSWORD;
        } else {
            message = UpdatePersonPasswordMessages.UNSUCCESSFULLY_CHANGED_PASSWORD;
            return new UserWithChangedPasswordDto(new UserTokenStateDto(jwt), message);
        }

        return new UserWithChangedPasswordDto(new UserTokenStateDto(jwt), message);
    }
}
