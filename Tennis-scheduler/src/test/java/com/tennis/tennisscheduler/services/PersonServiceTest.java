package com.tennis.tennisscheduler.services;

import com.tennis.tennisscheduler.models.Person;
import com.tennis.tennisscheduler.models.enumes.Gender;
import com.tennis.tennisscheduler.repositories.AddressRepository;
import com.tennis.tennisscheduler.repositories.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    PersonRepository personRepository;
    PersonService personService;
    Date date_s = new Date();
    @BeforeEach
    void setUp() {
        personService = new PersonService(personRepository);
    }

    @Test
    void canGetAllPersons() {

        Person person1 = Person.builder()
                .id(0)
                .firstName("Zoran")
                .lastName("Zoranovic")
                .email("zoran@gmail.com")
                .birthday(date_s)
                .gender(Gender.MALE)
                .address(null)
                .phoneNumber("0621234567")
                .build();

        Person person2 = Person.builder()
                .id(1)
                .firstName("Milana")
                .lastName("Milanovic")
                .email("milana@gmail.com")
                .birthday(date_s)
                .gender(Gender.FEMALE)
                .address(null)
                .phoneNumber("062121231237")
                .build();

        List<Person> personList = List.of(person1,person2);
        doReturn(personList).when(personRepository).findAll();
        List<Person> list2 = personService.getAllPersons();

        assertEquals(personList,list2);
    }
    @Test
    void canSavePerson() {
        //given

        Person person = Person.builder()
                .id(0)
                .firstName("Zoran")
                .lastName("Zoranovic")
                .email("zoran@gmail.com")
                .birthday(date_s)
                .gender(Gender.MALE)
                .address(null)
                .phoneNumber("0621234567")
                .build();
        //when
        personService.savePerson(person);
        //then
        ArgumentCaptor<Person> personArgumentCaptor =
                ArgumentCaptor.forClass(Person.class);
        verify(personRepository).save(personArgumentCaptor.capture());

        assertThat(personArgumentCaptor.getValue()).isEqualTo(person);
    }

    @Test
    void canGetPersonById() {

        Person person = Person.builder()
                .id(0)
                .firstName("Zoran")
                .lastName("Zoranovic")
                .email("zoran@gmail.com")
                .birthday(date_s)
                .gender(Gender.MALE)
                .address(null)
                .phoneNumber("0621234567")
                .build();

        doReturn(person).when(personRepository).findById(0);
        // Make the service call
        Person personByService = personService.findById(0);
        // Assert the response
        assertNotNull(personByService,"TennisCourt with this id: "+person.getId()+" not found");
        assertEquals(person.getId(),personByService.getId());
        assertEquals(person.getFirstName(), personByService.getFirstName());
        assertEquals(person.getLastName(), personByService.getLastName());
        assertEquals(person.getGender(), personByService.getGender());
        assertEquals(person.getPhoneNumber(), personByService.getPhoneNumber());
        assertEquals(person.getEmail(), personByService.getEmail());
        assertEquals(person.getBirthday(), personByService.getBirthday());
        assertEquals(person.getAddress(), personByService.getAddress());
    }
    @Test
    void canDeletePersonById() {
        Person person = Person.builder()
                .id(0)
                .firstName("Zoran")
                .lastName("Zoranovic")
                .email("zoran@gmail.com")
                .birthday(date_s)
                .gender(Gender.MALE)
                .address(null)
                .phoneNumber("0621234567")
                .build();

        personService.deletePersonById(person.getId());

        verify(personRepository).deleteById(person.getId());
    }
    @Test
    void canUpdatePerson() {

        long id = 0L;

        Person existingPerson = Person.builder()
                .id(0)
                .firstName("Zoran")
                .lastName("Zoranovic")
                .email("zoran@gmail.com")
                .birthday(date_s)
                .gender(Gender.MALE)
                .address(null)
                .phoneNumber("0621234567")
                .build();

        when(personRepository.findById(id))
                .thenReturn(existingPerson);

        ArgumentCaptor<Person> personArgumentCaptor =
                ArgumentCaptor.forClass(Person.class);

        Person updatedPerson = new Person();
        updatedPerson.setFirstName("Marko");
        updatedPerson.setLastName("Markovic");
        updatedPerson.setGender(Gender.MALE);
        updatedPerson.setEmail("marko@mail.com");
        updatedPerson.setPhoneNumber("012345566");
        personService.updatePerson(id,updatedPerson);
        verify(personRepository).save(personArgumentCaptor.capture());

        assertThat(personArgumentCaptor
                .getValue()
                .getFirstName())
                .isEqualTo("Marko");
        assertThat(personArgumentCaptor
                .getValue()
                .getLastName())
                .isEqualTo("Markovic");
        assertThat(personArgumentCaptor
                .getValue()
                .getGender())
                .isEqualTo(Gender.MALE);
        assertThat(personArgumentCaptor
                .getValue()
                .getEmail())
                .isEqualTo("marko@mail.com");
        assertThat(personArgumentCaptor
                .getValue()
                .getPhoneNumber())
                .isEqualTo("012345566");
    }
}