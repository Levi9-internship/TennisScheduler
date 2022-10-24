package com.tennis.tennisscheduler.services;

import com.tennis.tennisscheduler.models.Person;
import com.tennis.tennisscheduler.repositories.AddressRepository;

import com.tennis.tennisscheduler.repositories.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public List<Person> getAllPersons(){
        return personRepository.findAll();
    }
    
    public Person findById(long id){
        return personRepository.findById(id);
    }
    
    public Person savePerson(Person person) {
        return personRepository.save(person);
    }
    
    public void deletePersonById(long id){
        personRepository.deleteById(id);
    }
    
    public Person updatePerson(long id,Person person){
        Person existingPerson = personRepository.findById(id);
        existingPerson.setFirstName(person.getFirstName());
        existingPerson.setLastName(person.getLastName());
        existingPerson.setBirthday(person.getBirthday());
        existingPerson.setEmail(person.getEmail());
        existingPerson.setPhoneNumber(person.getPhoneNumber());
        existingPerson.setGender(person.getGender());
        existingPerson.setAddress(person.getAddress());
        return personRepository.save(existingPerson);
   }
    
}
