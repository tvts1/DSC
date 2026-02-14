package com.ifpe.clinica.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EstadoValidoValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EstadoValido {
    String message() default "Estado inválido. Use uma sigla válida (ex: PE, SP).";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}