package io.inventi.accountmanagement.repositories;

import io.inventi.accountmanagement.model.Statement;
import io.inventi.accountmanagement.repositories.custom.StatementRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatementRepository extends JpaRepository<Statement, Long>, StatementRepositoryCustom {
}
