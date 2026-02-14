package com.ifpe.clinica.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DiaUtilValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DiaUtil {
    String message() default "A data deve ser um dia útil (segunda a sexta).";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
