package com.tennis.tennisscheduler.validators;

import com.tennis.tennisscheduler.dtos.TimeslotDto;
import org.joda.time.DateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class FutureDateValidatorTest {

    ConstraintValidatorContext constraintValidatorContext;
    ConstraintValidator futureDateValidator;

    @BeforeEach
    void setUp() {
        futureDateValidator = new FutureDateValidator();
    }

    @Test
    void futureDateValidatorValid(){
        TimeslotDto timeslotDto = TimeslotDto.builder()
                .dateStart(new DateTime(2023, 12, 12, 21,30).toDate())
                .dateEnd(new DateTime(2023, 12, 12, 22,30).toDate())
                .build();

        assertTrue(futureDateValidator.isValid(timeslotDto, constraintValidatorContext));
    }

    @Test
    void futureDateValidatorNotValid() {
        TimeslotDto timeslotDto = TimeslotDto.builder()
                .dateStart(new DateTime(2021, 12, 12, 21,30).toDate())
                .dateEnd(new DateTime(2021, 12, 12, 22,30).toDate())
                .build();

        assertFalse(futureDateValidator.isValid(timeslotDto, constraintValidatorContext));
    }
}
