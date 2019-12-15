package com.group.chat.constraint;

import com.group.chat.validator.OnlyNumberAndLetterConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = OnlyNumberAndLetterConstraintValidator.class)
public @interface OnlyNumberAndLetterConstraint {

    String message();

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
