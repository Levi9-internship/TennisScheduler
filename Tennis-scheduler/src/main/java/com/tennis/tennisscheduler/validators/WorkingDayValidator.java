package com.tennis.tennisscheduler.validators;

import com.tennis.tennisscheduler.dtos.TimeslotDto;
import com.tennis.tennisscheduler.models.enumes.WorkingHours;
import com.tennis.tennisscheduler.validators.annotations.WorkingDayValidate;
import org.joda.time.DateTimeConstants;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class WorkingDayValidator implements ConstraintValidator<WorkingDayValidate, TimeslotDto> {

    @Override
    public void initialize(WorkingDayValidate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(TimeslotDto timeslotDto, ConstraintValidatorContext constraintValidatorContext) {
        if(timeslotDto.dateStart.getDay() == DateTimeConstants.SATURDAY
                || timeslotDto.dateStart.getDay() == DateTimeConstants.SUNDAY){
            if(timeslotDto.dateStart.getHours() < WorkingHours.WEEKEND.startHours
                    || timeslotDto.dateEnd.getHours() >= WorkingHours.WEEKEND.endHours)
                return false;
        }
        else{
            if(timeslotDto.dateStart.getHours() < WorkingHours.WORKING_DAYS.startHours
                    ||  timeslotDto.dateEnd.getHours() >= WorkingHours.WORKING_DAYS.endHours)
                return false;
        }
        return true;
    }
}
