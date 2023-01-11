package com.tennis.tennisscheduler.service;

import com.tennis.tennisscheduler.exception.ApiRequestException;
import com.tennis.tennisscheduler.model.Person;
import com.tennis.tennisscheduler.model.enumes.UserType;

import com.tennis.tennisscheduler.repository.PersonRepository;
import com.tennis.tennisscheduler.repository.RoleRepository;
import com.tennis.tennisscheduler.security.SecurityContextUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PersonService{

    private final PersonRepository personRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    public List<Person> getAllPersons() {

        if(SecurityContextUtil.GetLoggedUser().getRole().getRoleName().equals(UserType.ROLE_TENNIS_PLAYER))
            return personRepository.findAll().stream()
                    .filter(person -> !((person.getRole().getRoleName().equals(UserType.ROLE_ADMIN))))
                    .collect(Collectors.toList());
        else
            return personRepository.findAll().stream().collect(Collectors.toList());
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
        Person existingPerson = personRepository.findById(id).get();

        if (bCryptPasswordEncoder.matches(oldPassword, existingPerson.getPassword())) {
            existingPerson.setPassword(bCryptPasswordEncoder.encode(newPassword));
            personRepository.save(existingPerson);
        }
        else
            return false;

        return true;
    }
}
