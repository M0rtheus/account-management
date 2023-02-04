package io.inventi.accountmanagement.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public interface AccountService {

    void insertStatement(MultipartFile file);

    boolean isCSVFormat(MultipartFile file);

    Resource getStatement(Optional<LocalDate> dateFrom, Optional<LocalDate> dateTo);

    BigDecimal getAccountBalance(String accountNumber, Optional<LocalDate> dateFrom, Optional<LocalDate> dateTo);
}
