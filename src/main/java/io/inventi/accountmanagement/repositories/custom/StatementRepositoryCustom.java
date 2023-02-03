package io.inventi.accountmanagement.repositories.custom;

import io.inventi.accountmanagement.model.Statement;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StatementRepositoryCustom {

    List<Statement> getStatesByOperationDate(Optional<LocalDate> dateFrom, Optional<LocalDate> dateTo);
}
