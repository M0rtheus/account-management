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

//    Error messages
    public static final String CSV_PARSE_ERROR_MESSAGE = "Failed to parse CSV file: ";

//    File Types
    public static final String CSV_TYPE = "text/csv";
}
