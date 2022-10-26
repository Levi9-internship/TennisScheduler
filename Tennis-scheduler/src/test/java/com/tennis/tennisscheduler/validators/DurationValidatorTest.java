package com.tennis.tennisscheduler.validators;

import com.tennis.tennisscheduler.dtos.TimeslotDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class DurationValidatorTest {

    ConstraintValidatorContext constraintValidatorContext;
    ConstraintValidator durationValidator;

    @BeforeEach
    void setUp() {
        durationValidator = new DurationValidator();
    }

    @Test
    void durationTooLong(){
        TimeslotDto timeslotDto = TimeslotDto.builder()
                .dateStart(new Date(2022, 12,12,18,30))
                .dateEnd(new Date(2022, 12,12,22,30))
                .build();

        boolean valid = durationValidator.isValid(timeslotDto, constraintValidatorContext);

        assertFalse(valid);
    }

    @Test
    void durationTooShort(){
        TimeslotDto timeslotDto = TimeslotDto.builder()
                .dateStart(new Date(2022, 12,12,19,30))
                .dateEnd(new Date(2022, 12,12,19,45))
                .build();

        boolean valid = durationValidator.isValid(timeslotDto, constraintValidatorContext);

        assertFalse(valid);
    }

    @Test
    void durationValid(){
        TimeslotDto timeslotDto = TimeslotDto.builder()
                .dateStart(new Date(2022, 12,12,18,00))
                .dateEnd(new Date(2022, 12,12,19,00))
                .build();

        boolean valid = durationValidator.isValid(timeslotDto, constraintValidatorContext);

        assertTrue(valid);
    }

}