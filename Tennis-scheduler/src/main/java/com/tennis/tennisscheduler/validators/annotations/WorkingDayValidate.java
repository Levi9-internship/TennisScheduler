package com.tennis.tennisscheduler.validators.annotations;

import com.tennis.tennisscheduler.models.TimeslotMessage;
import com.tennis.tennisscheduler.validators.WorkingDayValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = WorkingDayValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface WorkingDayValidate {
    String message() default TimeslotMessage.workingTimeMessage;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}