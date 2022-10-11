package com.tennis.tennisscheduler.services;

import com.tennis.tennisscheduler.models.Address;
import com.tennis.tennisscheduler.models.Person;
import com.tennis.tennisscheduler.repositories.AddressRepository;
import com.tennis.tennisscheduler.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;
    private List<Person> persons;
    @Autowired
    private AddressRepository addressRepository;

    public List<Person> getAllPersons(){
        return personRepository.findAll();
    }
    public Person getPersonsById(long id) {
        return personRepository.findById(id);
    }
    public Person savePerson(Person person) {
        this.addressRepository.save(person.getAddress());
        return personRepository.save(person);
    }
    public void deletePerson(Person person){
        personRepository.delete(person);
    }
    public void deletePersonById(long id){
        Person person = personRepository.findById(id);
        personRepository.deleteById(id);
        this.addressRepository.delete(person.getAddress());
    }

    public Person updatePerson(long id,Person person){
        Person existingPerson = this.personRepository.findById(id);
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
