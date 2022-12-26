package com.tennis.tennisscheduler.validators;

import com.tennis.tennisscheduler.dtos.TimeslotDto;
import com.tennis.tennisscheduler.repositories.TimeslotRepository;
import com.tennis.tennisscheduler.validators.annotations.OverlappingTimeslotsValidate;
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
        if (timeslotRepository.overlappingWithStartOfExistingTimeslot(timeslotDto.dateStart, timeslotDto.dateEnd, timeslotDto.courtId, timeslotDto.id).size() > 0
        || timeslotRepository.overlappingWithEndOfExistingTimeslot(timeslotDto.dateStart, timeslotDto.dateEnd, timeslotDto.courtId, timeslotDto.id).size() > 0
        || timeslotRepository.overlappingWithStartAndEndOfExistingTimeslot(timeslotDto.dateStart, timeslotDto.dateEnd, timeslotDto.courtId, timeslotDto.id).size() > 0
        || timeslotRepository.overlappingWithMiddleOfExistingTimeslot(timeslotDto.dateStart, timeslotDto.dateEnd, timeslotDto.courtId, timeslotDto.id).size() > 0)
            return false;

        return true;

    }
}
