package com.tennis.tennisscheduler.validators;

import com.tennis.tennisscheduler.dtos.TimeslotDto;
import com.tennis.tennisscheduler.repositories.TimeslotRepository;
import com.tennis.tennisscheduler.validators.annotations.IsReservedValidate;
import lombok.AllArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
@AllArgsConstructor
public class IsReservedValidator implements ConstraintValidator<IsReservedValidate, TimeslotDto> {
    private final TimeslotRepository timeslotRepository;

    @Override
    public void initialize(IsReservedValidate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(TimeslotDto timeslotDto, ConstraintValidatorContext constraintValidatorContext) {
        if (timeslotRepository.checkIfTimeslotIsAlreadyReserved(timeslotDto.dateStart,timeslotDto.personId, timeslotDto.id) == null)
            return true;
        else
            return false;
    }
}
