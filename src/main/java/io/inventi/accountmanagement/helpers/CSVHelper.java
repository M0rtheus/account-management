package io.inventi.accountmanagement.helpers;

import io.inventi.accountmanagement.constants.Constants;
import io.inventi.accountmanagement.exceptions.CSVNotValidException;
import io.inventi.accountmanagement.model.Statement;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.apache.commons.csv.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class CSVHelper {

    public static List<Statement> parseCSVToStatements(InputStream inputStream) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.builder().setHeader().setIgnoreHeaderCase(true).setTrim(true).setNullString("").build())) {

            List<Statement> statements = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                Statement statement = new Statement(
                        csvRecord.get(Constants.STATE_COLUMN_ACCOUNT_NUMBER),
                        csvRecord.get(Constants.STATE_COLUMN_OPERATION_DATE_TIME) != null ? LocalDateTime.parse(csvRecord.get(Constants.STATE_COLUMN_OPERATION_DATE_TIME)) : null,
                        csvRecord.get(Constants.STATE_COLUMN_BENEFICIARY),
                        csvRecord.get(Constants.STATE_COLUMN_COMMENT),
                        csvRecord.get(Constants.STATE_COLUMN_AMOUNT) != null ? new BigDecimal(csvRecord.get(Constants.STATE_COLUMN_AMOUNT)) : null,
                        csvRecord.get(Constants.STATE_COLUMN_CURRENCY)
                );
                statements.add(statement);
            }

            validateStatements(statements);
            return statements;

        } catch (IOException | CSVNotValidException e) {
            throw new RuntimeException(Constants.CSV_PARSE_ERROR_MESSAGE + e.getMessage());
        }
    }

    private static void validateStatements(List<Statement> statements) throws CSVNotValidException {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        List<String> validationErrors = new ArrayList<>();

        for (Statement statement : statements){
            Set<ConstraintViolation<Statement>> violations = validator.validate(statement);
            if (violations.isEmpty()){
                continue;
            }
            for (ConstraintViolation<Statement> violation : violations) {
                validationErrors.add("Row " + (statements.indexOf(statement) + 1) + ": " + violation.getPropertyPath() + " " + violation.getMessage());
            }
        }

        if (!validationErrors.isEmpty()){
            throw new CSVNotValidException(String.join(", ", validationErrors));
        }
    }

    public static boolean isCSVFormat(MultipartFile file) {
        return Constants.CSV_TYPE.equals(file.getContentType());
    }

    public static ByteArrayInputStream parseStatementsToCSV(List<Statement> statements) {

            try (ByteArrayOutputStream out = new ByteArrayOutputStream();
                 CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out),
                     CSVFormat.DEFAULT.builder().setHeader(
                             Constants.STATE_COLUMN_ACCOUNT_NUMBER,
                             Constants.STATE_COLUMN_OPERATION_DATE_TIME,
                             Constants.STATE_COLUMN_BENEFICIARY,
                             Constants.STATE_COLUMN_COMMENT,
                             Constants.STATE_COLUMN_AMOUNT,
                             Constants.STATE_COLUMN_CURRENCY
                             ).build())) {

            for (Statement statement : statements) {
                List<String> data = Arrays.asList(
                        statement.getAccountNumber(),
                        String.valueOf(statement.getOperationDate()),
                        statement.getBeneficiary(),
                        statement.getComment(),
                        String.valueOf(statement.getAmount()),
                        statement.getCurrency()
                );

                csvPrinter.printRecord(data);
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(Constants.CSV_WRITE_ERROR_MESSAGE + e.getMessage());
        }
    }
}
