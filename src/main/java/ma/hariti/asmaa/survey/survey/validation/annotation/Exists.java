package ma.hariti.asmaa.survey.survey.validation.annotation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ma.hariti.asmaa.survey.survey.validation.validator.ExistsValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistsValidator.class)
public @interface Exists {
    String message() default "Resource does not exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    Class<?> entity();
}