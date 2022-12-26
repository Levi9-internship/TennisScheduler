package com.tennis.tennisscheduler.validators.annotations;

import com.tennis.tennisscheduler.messages.TimeslotMessage;
import com.tennis.tennisscheduler.validators.SameDateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SameDateValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface SameDateValidate {
    String message() default TimeslotMessage.SAME_DATE_MESSAGE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
