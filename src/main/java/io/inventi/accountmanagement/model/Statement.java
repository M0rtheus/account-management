package io.inventi.accountmanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class Statement {

    @Id
    @GeneratedValue
    private long id;
    private String accountNumber;
    private LocalDateTime operationDate;
    private String beneficiary;
    private String comment;
    private BigDecimal amount;
    private String currency;
}
