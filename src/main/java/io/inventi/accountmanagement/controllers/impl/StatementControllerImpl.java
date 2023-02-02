package io.inventi.accountmanagement.controllers.impl;

import io.inventi.accountmanagement.controllers.StatementController;
import io.inventi.accountmanagement.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;

@CrossOrigin
@RestController
public class StatementControllerImpl implements StatementController {

    @Autowired
    AccountService accountService;

    @Override
    public ResponseEntity<Void> insertStatement(MultipartFile file) {
        if(accountService.isCSVFormat(file)){
            accountService.insertStatement(file);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @Override
    public ResponseEntity<Resource> getStatement(LocalDate dateFrom, LocalDate dateTo) {
        return null;
    }

    @Override
    public ResponseEntity<BigDecimal> getAccountBalance(String accountNumber, LocalDate dateFrom, LocalDate dateTo) {
        return null;
    }
}
