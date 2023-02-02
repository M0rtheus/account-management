package io.inventi.accountmanagement.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface AccountService {

    void uploadStatement(MultipartFile file);

    Resource downloadStatement(LocalDate dateFrom, LocalDate dateTo);

    BigDecimal getAccountBalance(String accountNumber, LocalDate dateFrom, LocalDate dateTo);
}
