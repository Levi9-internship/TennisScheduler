package com.tennis.tennisscheduler.validator.annotations;

import com.tennis.tennisscheduler.message.TimeslotMessage;
import com.tennis.tennisscheduler.validator.IsReservedValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IsReservedValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface IsReservedValidate {
    String message() default TimeslotMessage.ALREADY_RESERVED_MESSAGE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}