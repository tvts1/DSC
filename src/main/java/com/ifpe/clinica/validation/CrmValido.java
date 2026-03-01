package com.ifpe.clinica.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CrmValidador.class)
@Documented
public @interface CrmValido {

    String message() default "{medico.crm.valido}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}