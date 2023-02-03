package io.inventi.accountmanagement.repositories.custom.impl;

import io.inventi.accountmanagement.constants.Constants;
import io.inventi.accountmanagement.model.Statement;
import io.inventi.accountmanagement.repositories.custom.StatementRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StatementRepositoryCustomImpl implements StatementRepositoryCustom {

    @Autowired
    private EntityManager em;

    @Override
    public List<Statement> getStatesByOperationDate(Optional<LocalDate> dateFrom, Optional<LocalDate> dateTo) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Statement> cq = cb.createQuery(Statement.class);
        Root<Statement> statementRoot = cq.from(Statement.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.and());
        if(dateFrom.isPresent()){
            predicates.add(cb.greaterThanOrEqualTo(statementRoot.get(Constants.STATE_PARAMETER_OPERATION_DATE), dateFrom.get().atStartOfDay()));
        }
        if(dateTo.isPresent()){
            predicates.add(cb.lessThan(statementRoot.get(Constants.STATE_PARAMETER_OPERATION_DATE), dateTo.get().plusDays(1).atStartOfDay()));
        }
        cq.where(predicates.toArray(new Predicate[0])).orderBy(cb.desc((statementRoot.get(Constants.STATE_PARAMETER_OPERATION_DATE).as(LocalDateTime.class))));
        return em.createQuery(cq).getResultList();
    }
}
