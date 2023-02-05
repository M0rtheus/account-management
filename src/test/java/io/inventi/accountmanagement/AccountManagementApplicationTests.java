package io.inventi.accountmanagement;

import io.inventi.accountmanagement.helpers.CSVHelper;
import io.inventi.accountmanagement.model.Statement;
import io.inventi.accountmanagement.repositories.StatementRepository;
import io.inventi.accountmanagement.services.AccountService;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpUriRequest;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.HttpResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class AccountManagementApplicationTests {

    @Autowired
    AccountService accountService;

	@Autowired
	StatementRepository statementRepository;

	@Test
	public void givenCSVFile_whenConvertToStatement_thenOutputArrayCreated() throws IOException {
		//provide statement .csv file path
		File file = new File("");
		InputStream targetStream = new FileInputStream(file);
		List<Statement> statements = CSVHelper.parseCSVToStatements(targetStream);
		assertTrue(statements.size() > 0);
	}

    @Test
    public void givenDataArray_whenConvertToCSV_thenOutputCreated() {

        List<Statement> statements = new ArrayList<>();
        statements.add(new Statement(
                "LT111000011111000004",
                LocalDateTime.parse("2022-02-28T10:15:30"),
                "Person",
                "Notes",
                new BigDecimal("400.04"),
                "EUR"
        ));
        statements.add(new Statement(
                "LT111000011111000004",
                LocalDateTime.parse("2022-02-28T10:15:30"),
                "Person",
                "Notes",
                new BigDecimal("500.05"),
                "EUR"
        ));
        Resource resource = new InputStreamResource(CSVHelper.parseStatementsToCSV(statements));
        assertTrue(resource.exists());
    }


	@Test
	public void givenStatementArray_whenGetAccountBalance_thenOutputCorrect() {
		List<Statement> statements = new ArrayList<>();
		statements.add(new Statement(
				"LT111000011111000001",
				LocalDateTime.parse("2022-02-28T10:15:30"),
				"Person",
				"Notes 1",
				new BigDecimal("1.00"),
				"EUR"
		));
		statements.add(new Statement(
				"LT111000011111000001",
				LocalDateTime.parse("2022-02-28T10:15:30"),
				"Person",
				"Notes 2",
				new BigDecimal("2.01"),
				"EUR"
		));
		statements.add(new Statement(
				"LT111000011111000001",
				LocalDateTime.parse("2022-02-28T10:15:30"),
				"Person",
				"Notes 2",
				new BigDecimal("-2.51"),
				"EUR"
		));

		statementRepository.saveAllAndFlush(statements);

		BigDecimal balance = accountService.getAccountBalance("LT111000011111000001", Optional.empty(), Optional.empty());
		System.out.println(balance);
		assertEquals(new BigDecimal("0.50"), balance);
	}
	@Test
	public void givenMimeType_whenHttpResponse_thenTypeCSV()
			throws IOException {

		String mimeType = "application/csv";
		HttpUriRequest request = new HttpGet("http://localhost:8080/getStatementCSV");

		HttpResponse response = HttpClientBuilder.create().build().execute(request);

		String responseMimeType = response.getFirstHeader("Content-Type").getValue().split(";")[0].trim();
		assertEquals(mimeType, responseMimeType);
	}
}
