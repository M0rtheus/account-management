package io.inventi.accountmanagement.repositories;

import io.inventi.accountmanagement.model.Statement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatementRepository extends JpaRepository<Statement, Long> {
}
