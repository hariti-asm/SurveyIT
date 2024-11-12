package ma.hariti.asmaa.survey.survey.validation.validator;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ma.hariti.asmaa.survey.survey.validation.annotation.Exists;
import org.springframework.beans.factory.annotation.Autowired;

public class ExistsValidator implements ConstraintValidator<Exists, Long> {
    @Autowired
    private EntityManager entityManager;
    private Class<?> entity;

    @Override
    public void initialize(Exists constraintAnnotation) {
        this.entity = constraintAnnotation.entity();
    }

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext context) {
        if (id == null) return true;

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<?> root = query.from(entity);
        query.select(cb.count(root))
                .where(cb.equal(root.get("id"), id));

        return entityManager.createQuery(query)
                .getSingleResult() > 0;
    }
}