package com.tennis.tennisscheduler.validator;

import com.tennis.tennisscheduler.dto.TimeslotDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class SameDayValidatorTest {

    ConstraintValidatorContext constraintValidatorContext;
    ConstraintValidator sameDateValidator;

    @BeforeEach
    void setUp() {
        sameDateValidator = new SameDateValidator();
    }

    @Test
    void sameDateValidatorValid(){
        TimeslotDto timeslotDto = TimeslotDto.builder()
                .dateStart(new Date(2022, 12,12,18,30))
                .dateEnd(new Date(2022, 12,12,22,30))
                .build();

        assertTrue(sameDateValidator.isValid(timeslotDto, constraintValidatorContext));
    }

    @Test
    void sameDateValidatorNotValid() {
        TimeslotDto timeslotDto = TimeslotDto.builder()
                .dateStart(new Date(2022, 12, 12, 17, 30))
                .dateEnd(new Date(2022, 12, 13, 18, 30))
                .build();

        assertFalse(sameDateValidator.isValid(timeslotDto, constraintValidatorContext));
    }

}
