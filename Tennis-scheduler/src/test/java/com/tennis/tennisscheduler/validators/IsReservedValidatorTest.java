package com.tennis.tennisscheduler.validators;

import com.tennis.tennisscheduler.dtos.TimeslotDto;
import com.tennis.tennisscheduler.models.Person;
import com.tennis.tennisscheduler.models.Timeslot;
import com.tennis.tennisscheduler.repositories.TimeslotRepository;
import org.joda.time.DateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class IsReservedValidatorTest {
    ConstraintValidatorContext constraintValidatorContext;
    ConstraintValidator isReservedValidator;
    @Mock
    TimeslotRepository timeslotRepository;
    private Timeslot timeslot;
    private Person person;

    @BeforeEach
    void setUp() {
        isReservedValidator = new IsReservedValidator(timeslotRepository);
        setUpData();
    }

    void setUpData(){
        person = Person.builder().id(0).build();
        timeslot = Timeslot.builder()
                .id(1)
                .startDate(new DateTime(2021, 12, 12, 21,30).toDate())
                .endDate(new DateTime(2021, 12, 12, 22,30).toDate())
                .person(person)
                .build();
    }

    @Test
    void isReservedValidatorValid(){
        TimeslotDto timeslotDto = TimeslotDto.builder()
                .id(2)
                .dateStart(new DateTime(2021, 12, 12, 21,30).toDate())
                .dateEnd(new DateTime(2021, 12, 12, 22,30).toDate())
                .personId(1)
                .build();

        doReturn(null).when(timeslotRepository).checkIfTimeslotIsAlreadyReserved(timeslotDto.getDateStart(), timeslotDto.getPersonId(), timeslotDto.getId());

        assertTrue(isReservedValidator.isValid(timeslotDto, constraintValidatorContext));
    }

    @Test
    void isReservedValidatorNotValid() {
        TimeslotDto timeslotDto = TimeslotDto.builder()
                .id(2)
                .dateStart(new DateTime(2021, 12, 12, 21,30).toDate())
                .dateEnd(new DateTime(2021, 12, 12, 22,30).toDate())
                .personId(person.getId())
                .build();

        doReturn(timeslot).when(timeslotRepository).checkIfTimeslotIsAlreadyReserved(timeslotDto.getDateStart(), timeslotDto.getPersonId(), timeslotDto.getId());

        assertFalse(isReservedValidator.isValid(timeslotDto, constraintValidatorContext));
    }
}
