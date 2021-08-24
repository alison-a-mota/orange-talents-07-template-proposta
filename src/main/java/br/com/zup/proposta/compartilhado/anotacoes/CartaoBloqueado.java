package br.com.zup.proposta.compartilhado.anotacoes;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Valida se o cartão está bloqueado
 *
 * @author Alison Alves
 * @version 1.1.0
 */

@Documented
@Constraint(validatedBy = {CartaoBloqueadoValidator.class})
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)

public @interface CartaoBloqueado {

    String message() default "Este cartão está bloqueado";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String fieldName();

    Class<?> domainClass();
}
