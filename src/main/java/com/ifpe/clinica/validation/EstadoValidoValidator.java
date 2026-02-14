package com.ifpe.clinica.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class EstadoValidoValidator implements ConstraintValidator<EstadoValido, String> {

    private final List<String> estados =
            Arrays.asList("PE", "SP", "RJ", "MG", "BA", "CE", "AM", "DF");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;
        return estados.contains(value.toUpperCase());
    }
}
