package io.inventi.accountmanagement.controllers;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface StatementController {

    @PostMapping("/insertStatementCSV")
    ResponseEntity<Void> insertStatement(@RequestParam MultipartFile file);

    @GetMapping("/getStatementCSV")
    ResponseEntity<Resource> getStatement(@RequestParam LocalDate dateFrom, @RequestParam LocalDate dateTo);

    @GetMapping("/getAccountBalance")
    ResponseEntity<BigDecimal> getAccountBalance(@RequestParam String accountNumber, @RequestParam LocalDate dateFrom, @RequestParam LocalDate dateTo);
}
