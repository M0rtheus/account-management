package io.inventi.accountmanagement.helpers;

import io.inventi.accountmanagement.constants.Constants;
import io.inventi.accountmanagement.model.Statement;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CSVHelper {

    public static List<Statement> parseCSVToStatements(InputStream inputStream) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.builder().setHeader().setIgnoreHeaderCase(true).setTrim(true).build())) {

            List<Statement> statements = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                Statement statement = new Statement(
                        csvRecord.get(Constants.STATE_COLUMN_ACCOUNT_NUMBER),
                        LocalDateTime.parse(csvRecord.get(Constants.STATE_COLUMN_OPERATION_DATE_TIME)),
                        csvRecord.get(Constants.STATE_COLUMN_BENEFICIARY),
                        csvRecord.get(Constants.STATE_COLUMN_COMMENT),
                        new BigDecimal(csvRecord.get(Constants.STATE_COLUMN_AMOUNT)),
                        csvRecord.get(Constants.STATE_COLUMN_CURRENCY)
                );
                statements.add(statement);
            }

            return statements;
        } catch (IOException e) {
            throw new RuntimeException(Constants.CSV_PARSE_ERROR_MESSAGE + e.getMessage());
        }
    }

    public static boolean isCSVFormat(MultipartFile file) {
        return Constants.CSV_TYPE.equals(file.getContentType());
    }
}
