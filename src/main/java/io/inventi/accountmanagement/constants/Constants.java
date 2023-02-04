package io.inventi.accountmanagement.constants;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Constants {

//    State column names
    public static final String STATE_COLUMN_ACCOUNT_NUMBER = "Account number";
    public static final String STATE_COLUMN_OPERATION_DATE_TIME = "Operation date/time";
    public static final String STATE_COLUMN_BENEFICIARY = "Beneficiary";
    public static final String STATE_COLUMN_COMMENT = "Comment";
    public static final String STATE_COLUMN_AMOUNT = "Amount";
    public static final String STATE_COLUMN_CURRENCY = "Currency";

//    State parameter name
    public static final String STATE_PARAMETER_OPERATION_DATE = "operationDate";

//    Error messages
    public static final String CSV_PARSE_ERROR_MESSAGE = "Failed to parse CSV file: ";
    public static final String CSV_WRITE_ERROR_MESSAGE = "Failed to export CSV file: ";
    public static final String STATEMENT_SAVE_ERROR_MESSAGE = "Failed to save statement to DB: ";
    public static final String STATEMENT_CONSTRAINT_MESSAGE = "null value";

//    File types
    public static final String CSV_TYPE = "text/csv";

//    Media types
    public static final String CSV_MEDIA_TYPE = "application/csv";

//    HTTP headers
    public static final String HTTP_HEADER_VALUE_ATTACHMENT_FILENAME = "attachment; filename=";

//    File name
    public static final String CSV_FILE_NAME = "Statements";
}
