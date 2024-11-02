package com.example.parcialds.validaciones;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class ADNValidacion implements ConstraintValidator<ADNValida, String[]> {
    private static final String VALID_CHARACTERS = "AGTC";


    @Override
    public void initialize(ADNValida constraintAnnotation) {
    }

    @Override
    public boolean isValid(String[] dna, ConstraintValidatorContext context) {
        if (dna == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("El array no puede ser null").addConstraintViolation();
            return false;
        }

        // 2. Verificar si el array está vacío
        if (dna.length == 0) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("El array no puede estar vacío").addConstraintViolation();
            return false;
        }

        int n = dna.length;

        // 3. Verificar si el array es NxM en vez de NxN
        for (String row : dna) {
            if (row == null) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("El array no puede contener filas null").addConstraintViolation();
                return false;
            }

            if (row.length() != n) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("El array debe ser NxN, pero se encontró una fila de tamaño incorrecto").addConstraintViolation();
                return false;
            }
        }

        // 4. Verificar si contiene caracteres inválidos (números o letras no válidas)
        for (String row : dna) {
            for (char c : row.toCharArray()) {
                if (!VALID_CHARACTERS.contains(Character.toString(c))) {
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate("El array contiene caracteres inválidos. Solo se permiten: " + VALID_CHARACTERS).addConstraintViolation();
                    return false;
                }
            }
        }

        // Si pasa todas las validaciones, es válido
        return true;
    }
}
