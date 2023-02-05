package io.inventi.accountmanagement.exceptions;

public class CSVNotValidException extends  Exception {
    public CSVNotValidException(String errorMessage) {
        super(errorMessage);
    }
}
