package com.tennis.tennisscheduler.validator;

import com.tennis.tennisscheduler.dto.TimeslotDto;
import com.tennis.tennisscheduler.model.Person;
import com.tennis.tennisscheduler.repository.TimeslotRepository;
import com.tennis.tennisscheduler.validator.annotations.IsReservedValidate;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person user = (Person) authentication.getPrincipal();
        if (timeslotRepository.checkIfTimeslotIsAlreadyReserved(timeslotDto.getDateStart(), user.getId(), timeslotDto.getId()) == null)
            return true;

        return false;
    }
}
