package com.tennis.tennisscheduler.validator;

import com.tennis.tennisscheduler.dto.TimeslotDto;
import com.tennis.tennisscheduler.repository.TimeslotRepository;
import com.tennis.tennisscheduler.validator.annotations.OverlappingTimeslotsValidate;
import lombok.AllArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@AllArgsConstructor
public class OverlappingTimeslotsValidator implements ConstraintValidator<OverlappingTimeslotsValidate, TimeslotDto> {
    private final TimeslotRepository timeslotRepository;

    @Override
    public void initialize(OverlappingTimeslotsValidate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(TimeslotDto timeslotDto, ConstraintValidatorContext constraintValidatorContext) {
        if (timeslotRepository.checkOverlapping(timeslotDto.getDateStart(), timeslotDto.getDateEnd(), timeslotDto.getCourtId(), timeslotDto.getId()).size() > 0)
            return false;
        else
            return true;
    }
}
