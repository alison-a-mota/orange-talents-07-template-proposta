package br.com.zup.proposta.compartilhado.anotacoes;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CartaoBloqueadoValidator implements ConstraintValidator<CartaoBloqueado, Object> {

    private String atributo;
    private Class<?> classe;
    private String value2;
    @PersistenceContext

    private EntityManager entityManager;


    @Override
    public void initialize(CartaoBloqueado cartaoBloqueado) {
        atributo = cartaoBloqueado.fieldName();
        classe = cartaoBloqueado.domainClass();
        value2 = "BLOQUEADO";
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        Query query = entityManager.createQuery("SELECT 1 FROM " + classe.getName() + " WHERE " + atributo + "= :value and cartao_status = :value2");
        query.setParameter("value", value);
        query.setParameter("value2", value2);

        var resultList = query.getResultList();
        return resultList.isEmpty();
    }
}