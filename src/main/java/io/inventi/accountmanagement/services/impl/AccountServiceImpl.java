package io.inventi.accountmanagement.services.impl;

import io.inventi.accountmanagement.constants.Constants;
import io.inventi.accountmanagement.helpers.CSVHelper;
import io.inventi.accountmanagement.model.Statement;
import io.inventi.accountmanagement.repositories.StatementRepository;
import io.inventi.accountmanagement.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    StatementRepository statementRepository;

    @Override
    public void insertStatement(MultipartFile file) {
        try {
            List<Statement> statements = CSVHelper.parseCSVToStatements(file.getInputStream());
            statementRepository.saveAll(statements);
        } catch (IOException e) {
            throw new RuntimeException(Constants.CSV_PARSE_ERROR_MESSAGE + e.getMessage());
        } catch (TransactionSystemException e) {
            throw new RuntimeException(Constants.STATEMENT_SAVE_ERROR_MESSAGE + e.getMessage());
        }
    }

    @Override
    public boolean isCSVFormat(MultipartFile file) {
        return CSVHelper.isCSVFormat(file);
    }

    @Override
    public Resource getStatement(Optional<LocalDate> dateFrom, Optional<LocalDate> dateTo) {
        List<Statement> statements = statementRepository.getStatesByOperationDate(dateFrom, dateTo);
        return new InputStreamResource(CSVHelper.parseStatementsToCSV(statements));
    }

    @Override
    public BigDecimal getAccountBalance(String accountNumber, Optional<LocalDate> dateFrom, Optional<LocalDate> dateTo) {
        List<Statement> statements = statementRepository.getStatesByAccountNumberOperationDate(accountNumber, dateFrom, dateTo);
        if (statements.isEmpty()) {
            return null;
        }
        return statements.stream().map(Statement::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
