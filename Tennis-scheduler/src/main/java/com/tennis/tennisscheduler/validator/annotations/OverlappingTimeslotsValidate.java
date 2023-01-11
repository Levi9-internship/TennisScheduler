package com.tennis.tennisscheduler.validator.annotations;

import com.tennis.tennisscheduler.message.TimeslotMessage;
import com.tennis.tennisscheduler.validator.OverlappingTimeslotsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = OverlappingTimeslotsValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface OverlappingTimeslotsValidate {
    String message() default TimeslotMessage.OVERLAPPING_MESSAGE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}