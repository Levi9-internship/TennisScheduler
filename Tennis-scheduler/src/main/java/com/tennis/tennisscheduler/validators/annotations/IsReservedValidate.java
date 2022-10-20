package com.tennis.tennisscheduler.validators.annotations;

import com.tennis.tennisscheduler.models.TimeslotMessage;
import com.tennis.tennisscheduler.validators.IsReservedValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IsReservedValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface IsReservedValidate {
    String message() default TimeslotMessage.alreadyReservedMessage;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}