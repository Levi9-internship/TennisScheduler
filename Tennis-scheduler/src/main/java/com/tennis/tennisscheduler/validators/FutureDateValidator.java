package com.tennis.tennisscheduler.validators;

import com.tennis.tennisscheduler.dtos.TimeslotDto;
import com.tennis.tennisscheduler.validators.annotations.FutureDateValidate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

public class FutureDateValidator implements ConstraintValidator<FutureDateValidate, TimeslotDto> {

    @Override
    public void initialize(FutureDateValidate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(TimeslotDto timeslotDto, ConstraintValidatorContext constraintValidatorContext) {
        if(timeslotDto.getDateStart().before(new Date()) || timeslotDto.getDateEnd().before(new Date()))
            return false;
        return true;
    }
}
