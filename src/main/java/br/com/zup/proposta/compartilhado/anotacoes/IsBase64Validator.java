package br.com.zup.proposta.compartilhado.anotacoes;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsBase64Validator implements ConstraintValidator<IsBase64, Object> {

    @Override
    public void initialize(IsBase64 validaBase64) {
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        java.util.Base64.Decoder decoder = java.util.Base64.getDecoder();
        try {
            decoder.decode(String.valueOf(value));
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }
}