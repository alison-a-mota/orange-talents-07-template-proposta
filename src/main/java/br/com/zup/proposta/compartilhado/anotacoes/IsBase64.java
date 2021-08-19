package br.com.zup.proposta.compartilhado.anotacoes;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Valida se a string é Base64.
 * @author Alison Alves
 * @version 1.1.0
 */

@Documented
@Constraint(validatedBy = {IsBase64Validator.class})
@Target(FIELD)
@Retention(RUNTIME)

public @interface IsBase64 {

    //Mensagem caso não valide
    String message() default "A String não é Base64.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
