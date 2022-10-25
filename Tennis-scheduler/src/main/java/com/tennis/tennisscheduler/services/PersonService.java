package com.tennis.tennisscheduler.services;

import com.tennis.tennisscheduler.models.Person;
import com.tennis.tennisscheduler.models.enumes.UserType;
import com.tennis.tennisscheduler.repositories.AddressRepository;

import com.tennis.tennisscheduler.repositories.PersonRepository;
import com.tennis.tennisscheduler.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    public List<Person> getAllPersons(){
        return personRepository.findAll();
    }
    
    public Person findById(long id){
        return personRepository.findById(id);
    }
    
    public Person savePerson(Person person) {
        person.setRole(roleRepository.findByRoleName(UserType.ROLE_TENNIS_PLAYER));
        person.setEnabled(true);
        person.setPassword(bCryptPasswordEncoder.encode(person.getPassword()));
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


   public String updatePassword(long id, String oldPassword, String newPassword){
        Person existingPerson = personRepository.findById(id);

        String e = bCryptPasswordEncoder.encode(oldPassword);
        if (e.equals(existingPerson.getPassword()))
            existingPerson.setPassword(bCryptPasswordEncoder.encode(newPassword));
        else
            return "Current password is not correct!";

        return "Successfully changed password.";
   }
    
}
