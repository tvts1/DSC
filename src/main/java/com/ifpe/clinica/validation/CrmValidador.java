package com.ifpe.clinica.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CrmValidador implements ConstraintValidator<CrmValido, String> {

    @Override
    public void initialize(CrmValido constraintAnnotation) {
    }

    @Override
    public boolean isValid(String valor, ConstraintValidatorContext context) {
        if (valor == null || valor.trim().isEmpty()) {
            return true;
        }

        String regexCrm = "^\\d{4,10}[-/][A-Z]{2}$";
        
        return valor.matches(regexCrm);
    }
}