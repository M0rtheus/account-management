package io.inventi.accountmanagement.model;

import io.inventi.accountmanagement.constants.Constants;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Statement {

    public Statement(String accountNumber, LocalDateTime operationDate, String beneficiary, String comment, BigDecimal amount, String currency) {
        this.accountNumber = accountNumber;
        this.operationDate = operationDate;
        this.beneficiary = beneficiary;
        this.comment = comment;
        this.amount = amount;
        this.currency = currency;
    }

    @Id
    @GeneratedValue
    private long id;
    @NotNull(message = Constants.STATEMENT_CONSTRAINT_MESSAGE)
    private String accountNumber;
    @NotNull(message = Constants.STATEMENT_CONSTRAINT_MESSAGE)
    private LocalDateTime operationDate;
    @NotNull(message = Constants.STATEMENT_CONSTRAINT_MESSAGE)
    private String beneficiary;
    private String comment;
    @NotNull(message = Constants.STATEMENT_CONSTRAINT_MESSAGE)
    private BigDecimal amount;
    @NotNull(message = Constants.STATEMENT_CONSTRAINT_MESSAGE)
    private String currency;
}
