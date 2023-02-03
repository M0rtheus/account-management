package io.inventi.accountmanagement.controllers.impl;

import io.inventi.accountmanagement.constants.Constants;
import io.inventi.accountmanagement.controllers.AccountController;
import io.inventi.accountmanagement.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@CrossOrigin
@RestController
public class AccountControllerImpl implements AccountController {

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
    public ResponseEntity<Resource> getStatement(Optional<LocalDate> dateFrom, Optional<LocalDate> dateTo) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, Constants.HTTP_HEADER_VALUE_ATTACHMENT_FILENAME + Constants.CSV_FILE_NAME)
                .contentType(MediaType.parseMediaType(Constants.CSV_MEDIA_TYPE))
                .body(accountService.getStatement(dateFrom, dateTo));
    }

    @Override
    public ResponseEntity<BigDecimal> getAccountBalance(String accountNumber, LocalDate dateFrom, LocalDate dateTo) {
        return null;
    }
}
