package com.tennis.tennisscheduler.validators;

import com.tennis.tennisscheduler.dtos.TimeslotDto;
import com.tennis.tennisscheduler.validators.annotations.SameDateValidate;
import org.apache.commons.lang3.time.DateUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SameDateValidator implements ConstraintValidator<SameDateValidate, TimeslotDto> {

    @Override
    public void initialize(SameDateValidate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(TimeslotDto timeslotDto, ConstraintValidatorContext constraintValidatorContext) {
        if(timeslotDto.dateStart.after(timeslotDto.dateEnd) || !DateUtils.isSameDay(timeslotDto.dateStart, timeslotDto.dateEnd))
            return false;
        return true;
    }
}
