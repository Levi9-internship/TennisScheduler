package com.tennis.tennisscheduler.validators.annotations;

import com.tennis.tennisscheduler.messages.TimeslotMessage;
import com.tennis.tennisscheduler.validators.OverlappingTimeslotsValidator;

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