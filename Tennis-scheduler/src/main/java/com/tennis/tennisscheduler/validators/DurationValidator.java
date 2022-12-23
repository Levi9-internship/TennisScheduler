package com.tennis.tennisscheduler.validators;

import com.tennis.tennisscheduler.dtos.TimeslotDto;
import com.tennis.tennisscheduler.validators.annotations.DurationValidate;
import org.joda.time.DateTime;
import org.joda.time.Minutes;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DurationValidator  implements ConstraintValidator<DurationValidate, TimeslotDto> {

    @Override
    public void initialize(DurationValidate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(TimeslotDto timeslotDto, ConstraintValidatorContext constraintValidatorContext) {
        Minutes minutes = Minutes.minutesBetween(new DateTime(timeslotDto.getDateStart()), new DateTime(timeslotDto.getDateEnd()));
        if(minutes.isGreaterThan(Minutes.minutes(120)) || minutes.isLessThan(Minutes.minutes(30)))
            return false;

        return true;
    }
}
