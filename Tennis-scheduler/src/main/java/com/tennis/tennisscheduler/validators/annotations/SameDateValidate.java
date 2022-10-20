package com.tennis.tennisscheduler.validators.annotations;

import com.tennis.tennisscheduler.models.TimeslotMessage;
import com.tennis.tennisscheduler.validators.SameDateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SameDateValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface SameDateValidate {
    String message() default TimeslotMessage.sameDateMessage;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
