package com.tennis.tennisscheduler.service;

import com.tennis.tennisscheduler.model.Person;
import com.tennis.tennisscheduler.model.enumes.UserType;

import com.tennis.tennisscheduler.repository.PersonRepository;
import com.tennis.tennisscheduler.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PersonService{

    private final PersonRepository personRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Optional<Person> findById(long id) {
        return personRepository.findById(id);
    }

    public Person savePerson(Person person) {
        person.setRole(roleRepository.findByRoleName(UserType.ROLE_TENNIS_PLAYER));
        person.setEnabled(true);
        person.setPassword(bCryptPasswordEncoder.encode(person.getPassword()));
        return personRepository.save(person);
    }

    public void deletePersonById(long id) {
        personRepository.deleteById(id);
    }

    public Person updatePerson(long id, Person person) {
        Person existingPerson = personRepository.findById(id).get();
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
