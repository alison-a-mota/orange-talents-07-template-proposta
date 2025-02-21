package br.com.zup.proposta.compartilhado.anotacoes;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExistsByIdValidator implements ConstraintValidator<ExistsById, Object> {

    private String atributo;
    private Class<?> classe;
    @PersistenceContext

    private EntityManager entityManager;


    @Override
    public void initialize(ExistsById existsById) {
        atributo = existsById.fieldName();
        classe = existsById.domainClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        Query query = entityManager.createQuery("SELECT 1 FROM " + classe.getName() + " WHERE " + atributo + "= :value");
        query.setParameter("value", value);

        var resultList = query.getResultList();
        return !resultList.isEmpty();
    }
}