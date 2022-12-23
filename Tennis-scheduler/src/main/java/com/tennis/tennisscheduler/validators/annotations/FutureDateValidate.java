package com.tennis.tennisscheduler.validators.annotations;

import com.tennis.tennisscheduler.messages.TimeslotMessage;
import com.tennis.tennisscheduler.validators.FutureDateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FutureDateValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface FutureDateValidate {
    String message() default TimeslotMessage.FUTURE_DATE_MESSAGE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
